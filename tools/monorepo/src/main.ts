import { watch } from './utilities/watch'
import { Context } from "./context";
import { toPlantUml } from "./utilities/plantuml";

import { program } from 'commander';

program.option('-d, --debug', 'output extra debugging')

const options = program.opts();
if (options.debug) console.log(options);

program.command("graph").action(async () => {
    const context = await Context.init()
    const dependencies = context.showDependencies()
    console.log(toPlantUml(dependencies))
})

program.command("watch").action(async () => {
    const context = await Context.init()
    const workspaceProjects = context.workspaceProjects
    await watch(workspaceProjects, undefined)
})

program.parse();