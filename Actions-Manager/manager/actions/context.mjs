import os from 'os';

export const context = (() => {
    const nexusSecret = {
        user: "admin",
        password: "secret"
    }

    return {
        workdir: "./sample-app",
        isFailed: false,
        binariesBase: "~/runner/bin",
        additionalPath: [],
        runner: {
            type: os.type(),
            platform: os.platform(),
            release: os.release(),
            architecture: os.arch(),
            userInfo: os.userInfo(),
            tmpdir: os.tmpdir(),
            machine: os.machine(),
        },
        owner: "venthe",
        repository: "ventheRepo",
        secrets: {
            nexus: {
                ...nexusSecret,
                authorizationHeader: () => 'Basic ' + Buffer.from(`${nexusSecret.user}:${nexusSecret.password}`).toString('base64')
            }
        },
        links: {
            nexus: {
                hostname: "nexus",
                port: 8081,
            }
        }
    };
})()

export const withContext = (act) => async (step) => act(step, context)