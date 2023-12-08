import { TSConfig, type PackageJson, readPackageJSON, readTSConfig, resolvePackageJSON } from 'pkg-types'
import { glob } from 'glob'
import { SpawnOptionsWithoutStdio, spawn } from 'child_process'
import { ColorManager } from './cli'
import { buildDependencyTree, provideVerticesAndEdges } from '@venthe/dependency-graph'
import { resolve } from 'path'
import { getTsconfig } from 'get-tsconfig'

export class Context {
    root: PackageJson | undefined
    // FIXME: To object
    workspaceProjects?: { directory: string, package: PackageJson, tsconfig?: TSConfig }[]
    tree: undefined | string[][]

    private constructor() {
    }

    static async init(file: string = "./") {
        const context = new Context()

        context.root = await readPackageJSON(resolve(file))
        context.workspaceProjects = await this.loadWorkspaceProjects(context.root)

        return context
    }

    generateTree(...projects: string[]) {
        if (!this.workspaceProjects) throw new Error("context not being initialized")

        this.tree = this.parseDependencies(this._m(), projects)
        return this.tree
    }

    showDependencies(...projects: string[]) {
        if (!this.workspaceProjects) throw new Error("context not being initialized")

        return this.parseShowDependencies(this._m(), projects)
    }

    private _m() {
        return this.workspaceProjects?.map(a => ({
            name: a.package.name,
            directory: a.directory,
            dependencies: Object.entries({
                ...(a.package.dependencies ?? {}),
                ...(a.package.devDependencies ?? {})
            })
                .filter(e => e[1] === "*")
                .map(e => e[0])
        })) ?? []
    }

    private parseDependencies(deps: { name?: string, directory: string, dependencies: string[] }[], projects?: string[]) {
        return buildDependencyTree(this.depsForParseDependencies_(deps), projects)
    }

    private parseShowDependencies(deps: { name?: string, directory: string, dependencies: string[] }[], projects?: string[]) {
        return provideVerticesAndEdges(this.depsForParseDependencies_(deps), projects)
    }

    private depsForParseDependencies_(deps: { name?: string | undefined; directory: string; dependencies: string[] }[]): { [key: string]: { needs?: string[] | undefined } } {
        return deps.reduce((acc, val) => {
            if (val.name) {
                acc[val.name] = { needs: val.dependencies }
            }
            return acc
        }, {} as any)
    }

    private static async loadWorkspaceProjects(root: PackageJson) {
        const workspaces = (root.workspaces ?? []);
        const globbedPaths = (await Promise.all(workspaces.map(path => glob(path)))).flatMap(a => a);
        const loadedDataForDirectories = await Promise.all(globbedPaths.map(async path => ({
            directory: path,
            tsconfig: (getTsconfig(resolve(path))?.config) as TSConfig,
            package: await readPackageJSON(resolve(path))
        })))

        return loadedDataForDirectories
    }

    private static getLocalDependencies(p: PackageJson) {
        return [...Object.entries(p.devDependencies ?? {}), ...Object.entries(p.dependencies ?? {})]
            .filter(dep => dep[1].match(/^.*\*.*$/))
            .map(e => e[0])
            .map(e => e.trim())
    }

    async execute({ target, dryRun = false }: { target: string, dryRun: boolean }) {
        if (!this.tree) throw new Error("Tree not defined")
        const colorManager = new ColorManager()

        for (const i in this.tree) {
            const row = this.tree[i]
            console.log(`${row}`)
            console.group()
            // parallel
            for (const col of row) {
                // const taskConfig: undefined | Target = targets[target]
                const project = this.workspaceProjects?.filter(wp => wp.package.name === col)[0]
                if (!project) throw new Error("This should not happen")

                // cwd: project?.directory ?? "", 
                const opts = { workspace: project?.directory, prefix: `${project.directory} - ${col}:${target}`, colorizer: colorManager.nextColor(), dryRun, target }

                const runConditionally = async (action: (() => Promise<unknown>), async?: boolean) => {
                    if (async) {
                        action()
                    } else {
                        await action()
                    }
                }

                // await Promise.all(Object.entries(taskConfig?.runBefore ?? {})
                //     .map(el => runConditionally(async () => this.execTask(el[0], { ...opts, prefix: `${project.directory} - ${col}:${el[0]}` }), el[1].async)))

                await runConditionally(async () => this.execTask(target, opts), true) // taskConfig?.async
            }

            console.groupEnd()
        }
    }

    async execTask(task: string, opts?: SpawnOptionsWithoutStdio & { workspace?: string, prefix?: string, colorizer?: (data: string) => string, dryRun: boolean, target: string }) {
        const c = opts?.colorizer ?? ((a: string) => a)
        // TODO: Remove this set :)
        //  Too many newlines...
        const withPrefix = (data: string) => c([...new Set(data.split("\n").map(a => a.trimEnd()).filter(a => a.length > 0).map(d => opts?.prefix ? `[${opts?.prefix}] ${d}` : d))].join("\n"))

        console.debug(withPrefix(`Executing target ${task}`))

        return new Promise((resolve, reject) => {
            if (opts?.dryRun) {
                console.warn(withPrefix("DRY RUNNING"))
                resolve(0)
                return
            }

            const cmd = spawn(`npm run --silent ${opts?.workspace ? '--workspace=' + opts.workspace : ''} ${task}`, [], { ...(opts ?? {}), shell: true });

            cmd.stdout.on('data', (data) => {
                console.log(withPrefix(data.toString()));
            });

            cmd.stderr.on('data', (data) => {
                console.error(withPrefix(data.toString()));
            });

            cmd.on('exit', (code) => {
                const c = +(code ?? 0)
                if (c === 0) {
                    resolve(c)
                } else {
                    reject(c)
                }
            });
        })
    }
}