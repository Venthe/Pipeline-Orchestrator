import { shell } from "../library/process.mjs"

export const checkout = async (step, context) => {
    const repositoryType = step.with?.type ?? "GITHUB"

    if (repositoryType === "GITHUB") {
        console.log("Downloading from Github")

        await shell("git clone https://github.com/Venthe/test_project.git " + process.env.RUNNER_WORKDIR)
    }

}