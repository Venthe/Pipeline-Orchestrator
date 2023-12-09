import chokidar from 'chokidar'
import { byId, byType, setupBus } from './bus'
import { Subscriber, Subscription, debounceTime, filter, map, partition } from 'rxjs'
import path from 'path'

type WatchEvent = {
    event: string
    type: string
    file: string
    identifier: string
    date: string
}

const watchDirectories = (type: string, directories: string[], publishCallback, identifierProvider: (path: string) => string) => {
    return new Promise((resolve, reject) => {
        let track = false;

        const watcher = chokidar.watch(directories);

        const eventMapper = (event: string, path: string): WatchEvent[] => {
            const events: WatchEvent[] = []
            const date = new Date().toUTCString()

            const identifier = identifierProvider(path)

            if (identifier) events.push({ date, event, file: path, type, identifier })

            return events
        }

        watcher
            .on('add', (path, stats) => {
                if (!track) return
                publishCallback(...eventMapper('add', path))
            })
            .on('change', (path, stats) => {
                if (!track) return
                publishCallback(...eventMapper('change', path))
            })
            .on('unlink', path => {
                if (!track) return
                publishCallback(...eventMapper('unlink', path))
            })
            .on('addDir', (path, stats) => {
                if (!track) return
                publishCallback(...eventMapper('addDir', path))
            })
            .on('unlinkDir', path => {
                if (!track) return
                publishCallback(...eventMapper('unlinkDir', path))
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
}

export const watch = async (workspaceProjects, workspaceConfiguration) => {

    const projectDirectories = workspaceProjects.map(a => path.join(a.directory, "src"))
    const libraryDirectories = workspaceProjects.map(a => path.join("node_modules", a.package.name, "dist"))

    const { complete, publish, subscribe } = setupBus()

    const triggerForChildren = (project, type, event) => {
        console.log(project, type, event)
    }

    workspaceProjects?.map(w => w.package.name ?? w.package.directory)
        .forEach((id: string) => {
            subscribe(observable => observable.pipe(
                byId(id),
                byType("watch_event_project_source_file_changed")
            ).subscribe(e => triggerForChildren(id, "project", e)));
            subscribe(observable => observable.pipe(
                byId(id),
                byType("watch_event_project_out_file_changed")
            ).subscribe(e => triggerForChildren(id, "dependency", e)));
        })

    await new Promise((resolve, reject) => {
        Promise.all([
            watchDirectories("watch_event_project_source_file_changed", projectDirectories, publish, path => workspaceProjects?.filter(wp => path.startsWith(wp.directory)).map(wp => wp.package.name ?? wp.directory)[0]),
            watchDirectories("watch_event_project_out_file_changed", libraryDirectories, publish, path => workspaceProjects?.filter(wp => path.includes(`node_modules/${wp.package.name}`)).map(wp => wp.package.name ?? wp.directory)[0])
        ])
            .then(() => {
                complete()
            })
            .then(() => {
                resolve(0)
            })
    })

    // await context.execute({ target, dryRun }).catch(e => console.error("Execution error", e))

    // const taskConfig: undefined | Target = targets[target]
    // if (taskConfig?.async) {
    //     await new Promise((resolve, reject) => { });
    // }
}