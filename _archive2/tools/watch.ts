import chokidar from 'chokidar'
import { setupBus } from './bus'
import { debounceTime, filter, map, partition } from 'rxjs'
import path from 'path'

export const watch = async (workspaceProjects, workspaceConfiguration) => {

    const projectDirectories = workspaceProjects.map(a => path.join(a.directory, a.tsconfig.compilerOptions.outDir))
    const libraryDirectories = workspaceProjects.map(a => path.join("node_modules", a.package.name))

    const { bus, complete, publish } = setupBus()

    const triggerForChildren = (project, type) => {
        console.log(project, type)
    }

    const subscribers = workspaceProjects?.map(w => w.package.name ?? w.package.directory)
        .flatMap(id => {
            const observable = bus.pipe(
                filter(e => e.identifier === id),
                map(e => {
                    const { identifier, ...rest } = e;
                    return rest
                })
            )

            const [dependencies, projects] = partition(observable, (v, i) => v.type === 'dependency')

            const $throttleDependencies = dependencies.pipe(debounceTime(1000)).subscribe(e => triggerForChildren(id, "dependency"))
            const $throttleProjects = projects.pipe(debounceTime(1000)).subscribe(e => triggerForChildren(id, "project"))

            return [$throttleDependencies, $throttleProjects]
        })

    await new Promise((resolve, reject) => {
        // const watcher = chokidar.watch('file, dir, glob, or array', {
        //     ignored: /(^|[\/\\])\../, // ignore dotfiles
        //     persistent: true
        //   });

        let track = false;

        const watcher = chokidar.watch([...projectDirectories, ...libraryDirectories]);

        type Ev = {
            event: string
            type: string
            file: string
            identifier: string
            date: string
        }

        const eventMapper = (event: string, path: string): Ev[] => {
            const events: Ev[] = []
            const date = new Date().toUTCString()

            const projectDirectory = workspaceProjects?.filter(wp => path.startsWith(wp.directory))[0]
            if (projectDirectory) {
                events.push({ date, event, file: path, type: 'project', identifier: projectDirectory.package.name ?? projectDirectory.directory })
            }

            const dependencyDirectory = workspaceProjects?.filter(wp => path.includes(`node_modules/${wp.package.name}`))[0]
            if (dependencyDirectory) {
                events.push({ date, event, file: path, type: 'dependency', identifier: dependencyDirectory.package.name ?? dependencyDirectory.directory })
            }

            return events
        }

        watcher
            .on('add', (path, stats) => {
                if (!track) return
                publish(...eventMapper('add', path))
            })
            .on('change', (path, stats) => {
                if (!track) return
                publish(...eventMapper('change', path))
            })
            .on('unlink', path => {
                if (!track) return
                publish(...eventMapper('unlink', path))
            })
            .on('addDir', (path, stats) => {
                if (!track) return
                publish(...eventMapper('addDir', path))
            })
            .on('unlinkDir', path => {
                if (!track) return
                publish(...eventMapper('unlinkDir', path))
            })
            .on('error', error => {
                console.log(`Watcher error: ${error}`)
                reject(watcher.close())
            })
            .on('ready', () => {
                console.log('Initial scan complete. Ready for changes')
                track = true;
            })
        //.on('raw', (event, path, details) => { // internal
        //    if (!track) return
        //    log('Raw event info:', event, path, details);
        //});
    })

    // await context.execute({ target, dryRun }).catch(e => console.error("Execution error", e))

    // const taskConfig: undefined | Target = targets[target]
    // if (taskConfig?.async) {
    //     await new Promise((resolve, reject) => { });
    // }
}