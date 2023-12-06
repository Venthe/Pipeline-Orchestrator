import grunt from 'grunt'
import { Context } from '../tools/context'
import { watch } from '../tools/watch'
import { toPlantUml } from '../tools/plantuml'

const workspaceConfiguration = {
    "serve": {
        "applications/server": {
            target: "serve",
            runContinuously: true
        },
        "*": {
            target: "build"
        }
    }
}

grunt.initConfig({})

grunt.registerTask("combo", '', async function (...args: string[]) {
    this.async();

    const targets: string[] = (grunt.option('targets') as string)?.split(",") ?? []

    if (!targets) throw new Error("Targets needs to exist")

    const context = await Context.init()

    const workspaceProjects = context.workspaceProjects

    await watch(workspaceProjects, workspaceConfiguration)
})

grunt.registerTask("graph", '', async function (...args: string[]) {
    this.async();

    const context = await Context.init()
    const dependencies = context.showDependencies()
    console.log(toPlantUml(dependencies))
})
