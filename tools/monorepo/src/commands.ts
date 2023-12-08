import { Context } from './context'
import { watch } from './watch'
import { toPlantUml } from './plantuml'

const combo = async () => {
    // const targets: string[] = (grunt.option('targets') as string)?.split(",") ?? []

    // if (!targets) throw new Error("Targets needs to exist")

    // const context = await Context.init()

    // const workspaceProjects = context.workspaceProjects

    // await watch(workspaceProjects, workspaceConfiguration)
}

export const graph = async () => {
    const context = await Context.init()
    const dependencies = context.showDependencies()
    console.log(toPlantUml(dependencies))
}