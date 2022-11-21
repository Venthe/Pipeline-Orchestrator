import * as process from '../library/process.mjs'

export const shell = async (step) =>
    process.shell(step.run)