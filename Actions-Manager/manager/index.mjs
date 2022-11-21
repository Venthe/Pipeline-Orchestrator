import { shell } from './actions/shell.mjs'
import { shell as shell2 } from './library/process.mjs'
import { setupJava } from './actions/setup-java.mjs'
import { setupGradle } from './actions/setup-gradle.mjs'
import clc from "cli-color";
import { downloadArtifact } from './actions/download-artifact.mjs';
import { uploadArtifact } from './actions/upload-artifact.mjs';
import { context, withContext } from './actions/context.mjs'
import { checkout } from './actions/checkout.mjs';
import { loadSteps } from './library/configuration.mjs';
import { print } from './library/utilities.mjs';

const actions = {
    "Shell": shell,
    "actions/checkout@v1": withContext(checkout),
    "actions/setup-java@v1": withContext(setupJava),
    "actions/setup-gradle@v1": withContext(setupGradle),
    "actions/cache@v1": print,
    "actions/upload-artifact@v1": withContext(uploadArtifact),
    "actions/download-artifact@v1": withContext(downloadArtifact),
}

const handleStep = async (step) => {
    const type = step.uses ?? "Shell"
    const action = actions[type]
    if (action === undefined) {
        throw new Error(`No handler for the step for type ${type}`)
    }
    await action(step)
}

const shouldRun = (_if, context) => {
    if (_if === undefined) {
        return !context.isFailed
    }

    if (_if === "always()") {
        return true;
    }

    return (new Function(`{env, steps, isFailed, ...rest} = ${JSON.stringify(context)}`, `return ${_if}`))() && !context.isFailed
}

const getStepName = (step) => step.name ?? step.uses ?? "Shell"

const updateProcessEnv = () => ({
    ...process.env,
    ...context.env,
    PATH: [...context.additionalPath, process.env.PATH].join(":")
})

await (async () => {
    const steps = loadSteps()
    await shell2(`mkdir --parents ${context.binariesBase}`)

    for (const index in steps) {
        const step = steps[index]
        const finalName = getStepName(step);

        if (!shouldRun(step.if, context)) {
            console.log(`Skipping step ${finalName}...`)
            console.log()
            continue
        }

        process.env = updateProcessEnv()

        console.time(finalName)
        console.log(clc.white.bold(finalName))
        try {
            await handleStep(step)
        }
        catch (exception) {
            console.error(clc.red(exception.reason))
            context.isFailed = true
            context.reason = exception
        }
        console.timeEnd(finalName)
        console.log()
    }

    if (context.isFailed) {
        throw context.reason
    }
})()