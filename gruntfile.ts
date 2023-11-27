import grunt from 'grunt'
import YAML from 'yaml'
import fs from 'fs'
import path from 'path'
import type { PackageJson } from 'pkg-types'
import { glob } from 'glob'
import { buildDependencyTree } from './applications/server/src/jenkins/graphUtility'
import { SpawnOptionsWithoutStdio, spawn } from 'child_process'
import chalk from 'chalk';

class ColorManager {
    colors = [chalk.red, chalk.yellow, chalk.green, chalk.blueBright]
    currentColor = 0

    nextColor() {
        const ccolor = (this.currentColor + 0)
        this.currentColor++

        if (this.colors.length < this.currentColor) {
            this.currentColor = 0
        }

        return this.colors[ccolor]
    }
}

interface Target {
    async?: boolean
    pre?: Targets
}

type Targets = {[key: string]: Target}

const targets: Targets = {
    "build:watch": {
        async: true,
        pre: {
            "build": {
                async: false
            }
        }
    }
}

class Context {
    packages: { [key: string]: PackageJson } = {}
    // FIXME: To object
    private workspaceProjects?: { name?: string, directory: string, dependencies: string[] }[]
    tree: undefined | string[][]

    constructor(file: string) {
        this.packageJsonToRegistry(file)
    }

    async init() {
        this.workspaceProjects = await this.loadWorkspaceProjects()
    }

    generateTree(...projects: string[]) {
        if (!this.workspaceProjects) throw new Error("context not being initialized")

        this.tree = this.parseDependencies(this.workspaceProjects, projects)
        return this.tree
    }

    private packageJsonToRegistry(file: string) {
        const pj = this.loadPackageJson(file)
        this.packages["root"] = pj
    }

    private get rootPackage() {
        return this.packages["root"]
    }

    private loadPackageJson = (path: string): PackageJson => YAML.parse(
        fs.readFileSync(path, { encoding: 'utf8', flag: 'r' })
    )

    private getLocalDependencies(p: PackageJson) {
        return [...Object.entries(p.devDependencies ?? {}), ...Object.entries(p.dependencies ?? {})]
            .filter(dep => dep[1].match(/^.*\*.*$/))
            .map(e => e[0])
            .map(e => e.trim())
    }

    private parseDependencies(deps: { name?: string, directory: string, dependencies: string[] }[], projects?: string[]) {
        return buildDependencyTree(deps.reduce((acc, val) => {
            if (val.name) {
                acc[val.name] = { needs: val.dependencies }
            }
            return acc
        }, {} as any), projects)
    }

    private async loadWorkspaceProjects() {
        return (await Promise.all((this.rootPackage.workspaces ?? [])
            .map(path => glob(path))))
            .flatMap(a => a)
            .map(directory => ({ directory, package: this.loadPackageJson(path.join(directory, "package.json")) }))
            .map(entry => ({ directory: entry.directory.trim(), dependencies: this.getLocalDependencies(entry.package), name: entry.package.name?.trim() }))
    }

    async execute(target: string) {
        if (!this.tree) throw new Error("Tree not defined")
        const colorManager = new ColorManager()

        for (const i in this.tree) {
            const row = this.tree[i]
            console.log(`${row}`)
            console.group()
            // parallel
            for (const col of row) {
                const taskConfig: undefined | Target = targets[target]
                const project = this.workspaceProjects?.filter(wp => wp.name === col)[0]
                if (!project) throw new Error("This should not happen")

                const opts = { cwd: project?.directory ?? "", prefix: `${project.directory} - ${col}:${target}`, colorizer: colorManager.nextColor() }

                const runConditionally = async (action: (() => Promise<unknown>), async?: boolean) => {
                    if (async) {
                        action()
                    } else {
                        await action()
                    }
                }

                await Promise.all(Object.entries(taskConfig?.pre ?? {}).map(el => runConditionally(async () => this.execTask(el[0], {...opts, prefix: `${project.directory} - ${col}:${el[0]}`}), el[1].async)))

                await runConditionally(async () => this.execTask(target, opts), taskConfig?.async)
            }

            console.groupEnd()
        }
    }

    async execTask(task: string, opts?: SpawnOptionsWithoutStdio & { prefix?: string, colorizer?: (data: string) => string }) {
        const c = opts?.colorizer ?? ((a: string) => a)
        // TODO: Remove this set :)
        //  Too many newlines...
        const withPrefix = (data: string) => c([...new Set(data.split("\n").map(a => a.trimEnd()).filter(a => a.length > 0).map(d => opts?.prefix ? `[${opts?.prefix}] ${d}` : d))].join("\n"))

        console.debug(withPrefix(`Executing target ${task}`))

        return new Promise((resolve, reject) => {
            const cmd = spawn(`npm run --silent ${task}`, [], { ...(opts ?? {}), shell: true });

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

grunt.initConfig({})

grunt.registerTask("run-many", 'A sample task that logs stuff.', async function (...args: string[]) {
    this.async();

    const target: string | undefined = grunt.option('target')
    const options: string[] = (grunt.option('projects') as string)?.split(",") ?? []

    if (!target) throw new Error("!")

    console.log(target, options)

    const context = new Context("./package.json")
    await context.init()

    context.generateTree(...options)

    await context.execute(target).catch(e => console.error("Execution error", e))

    const taskConfig: undefined | Target = targets[target]
    if (taskConfig?.async) {
        await new Promise((resolve, reject) => { });
    }
})
