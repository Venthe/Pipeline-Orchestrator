import grunt from 'grunt'
import YAML from 'yaml'
import fs from 'fs'
import path from 'path'
import type { PackageJson } from 'pkg-types'
import { glob } from 'glob'
import { buildDependencyTree } from './applications/server/src/jenkins/graphUtility'
import { SpawnOptionsWithoutStdio, spawn } from 'child_process'


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
            acc[val.name] = { needs: val.dependencies }
            return acc
        }, {}), projects)
    }

    private async loadWorkspaceProjects() {
        return (await Promise.all((this.rootPackage.workspaces ?? [])
            .map(path => glob(path))))
            .flatMap(a => a)
            .map(directory => ({ directory, package: this.loadPackageJson(path.join(directory, "package.json")) }))
            .map(entry => ({ directory: entry.directory.trim(), dependencies: this.getLocalDependencies(entry.package), name: entry.package.name?.trim() }))
    }

    async execute(task: string) {
        if (!this.tree) throw new Error("Tree not defined")

        for (const i in this.tree) {
            const row = this.tree[i]
            console.log(`${+i + 1} - ${row}`)
            console.group()
            // parallel
            for (const col of row) {
                const project = this.workspaceProjects?.filter(wp => wp.name === col)[0]
                console.log(`${project.directory} - ${col}:${task}`)
                await this.execTask(task, {cwd: project?.directory ?? ""})
            }

            console.groupEnd()
        }
    }

    async execTask(task: string, opts?: SpawnOptionsWithoutStdio) {
        return new Promise((resolve, reject) => {
            const cmd = spawn(`npm`, ['run', task, "--silent"], opts);

            cmd.stdout.on('data', (data) => {
                console.log(data.toString());
            });
    
            cmd.stderr.on('data', (data) => {
                console.error(data.toString());
            });
    
            cmd.on('exit', (code) => {
                if(+code === 0) {
                    resolve(+code)
                } else {
                    reject(+code)
                }
            });
        })
    }
}

grunt.initConfig({})

grunt.registerTask("default", 'A sample task that logs stuff.', async function (...args: string[]) {
    this.async();

    const context = new Context("./package.json")
    await context.init()

    context.generateTree("@pipeline/types") // "@pipeline/core"
    await context.execute("build")
})
