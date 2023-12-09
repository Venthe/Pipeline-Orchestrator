import { TSConfig, type PackageJson, readPackageJSON, readTSConfig, resolvePackageJSON } from 'pkg-types'
import { glob } from 'glob'
import { SpawnOptionsWithoutStdio, spawn } from 'child_process'
import * as _path from 'path'

export const execTask = async (
    task: string,
    workspace?: string) => {
    const _task = `npm run --silent ${workspace ? '--workspace=' + workspace : ''} ${task}`
    return exec(_task)
}

export const execManager = () => {
    
}

export const exec = async (
    task: string,
    opts?: SpawnOptionsWithoutStdio & {
        prefix?: string,
        colorizer?: (data: string) => string,
        dryRun: boolean,
    }) => {
    const colorizeString = opts?.colorizer ?? ((a: string) => a)
    // TODO: Remove this set :)
    //  Too many newlines...
    const withPrefix = (data: string) => colorizeString([
        ...new Set(data.split("\n")
            .map(a => a.trimEnd())
            .filter(a => a.length > 0)
            .map(d => opts?.prefix ? `[${opts?.prefix}] ${d}` : d))
    ].join("\n"))

    console.debug(withPrefix(`Executing target ${task}`))

    return new Promise((resolve, reject) => {
        if (opts?.dryRun) {
            console.warn(withPrefix("Dry run: success"))
            resolve(0)
        }

        const cmd = spawn(task, [], { ...(opts ?? {}), shell: true });

        cmd.stdout.on('data', (data) => console.log(withPrefix(data.toString())));
        cmd.stderr.on('data', (data) => console.error(withPrefix(data.toString())));

        cmd.on('exit', (exitCode) => {
            const mappedCode = +(exitCode ?? 0)
            if (mappedCode === 0) {
                resolve(mappedCode)
            } else {
                reject(mappedCode)
            }
        });
    })
}