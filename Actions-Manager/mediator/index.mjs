import got from "got";
import http from 'http';

process.env.JENKINS_TOKEN = process.env.JENKINS_TOKEN ?? '11889d59e14e5e05499a2f66be1b03c4ef'
process.env.JENKINS_USER = process.env.JENKINS_USER ?? 'admin'
process.env.JENKINS_URL = process.env.JENKINS_URL ?? 'http://localhost:8082'
process.env.SERVER_PORT = process.env.SERVER_PORT ?? '8084'

const callJenkinsJob = async (jobName, parameters, retryStream) => new Promise((success, fail) => {
    const stream = retryStream ?? got.stream(
        new URL(`${process.env.JENKINS_URL}/job/${jobName}/${parameters == undefined ? 'build' : 'buildWithParameters'}`),
        {
            method: 'POST',
            username: `${process.env.JENKINS_USER}`,
            password: `${process.env.JENKINS_TOKEN}`,
            headers: {
                ...(parameters != undefined ? { "Content-Type": "application/x-www-form-urlencoded" } : {})
            },
            ...(parameters === undefined ? { body: '' } : {}),
            ...(parameters !== undefined ? { form: parameters } : {})
        }
    );

    stream.on('response', async ({ body, statusCode, ...response }) => {
        console.debug("response", statusCode, body);
        statusCode === 201
            ? success({ statusCode, ...(body ?? {}) })
            : fail({ statusCode, ...(body ?? {}) })
    });
    stream.on('downloadProgress', async progress => {
        console.debug("downloadProgress", progress);
    });
    stream.on('uploadProgress', async progress => {
        console.debug("uploadProgress", progress);
    });
    stream.once('retry', async (retryCount, error, createRetryStream) => {
        console.debug("retry", retryCount);
        console.error("retry", error);
        callJenkinsJob(jobName, parameters, createRetryStream)
    });
    stream.once('error', async ({ code, body, ...error }) => {
        console.error("error", code); success({ statusCode: 500, body: code });
    });
    stream.once('redirect', async (updatedOptions, response) => {
        console.debug("redirect", updatedOptions, response);
    });
    // stream.on('request', async ({ host, protocol, path, ...request }) => { console.debug("request", protocol, host, path); });
})

const server = http.createServer();

server.on('request', async (req, res) => {
    if (req.method === 'POST') {
        let body = "";
        req.on('data', (chunk) => { body += chunk })
        req.once('end', async () => {
            try {
                // const { job, parameters } = JSON.parse(body);
                // const response = await callJenkinsJob(job, parameters)
                // res.writeHead(response.statusCode)
                // res.end(response.body ?? undefined)
                console.log(JSON.stringify(JSON.parse(body), undefined, 2))
                res.writeHead(200)
                res.end()
            } catch (e) {
                console.error(e)
                res.writeHead(500)
                res.end()
            }
        })
    } else {
        res.writeHead(500)
        res.end()
    }
});

server.listen(process.env.SERVER_PORT);