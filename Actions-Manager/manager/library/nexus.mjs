import path from 'path'
import fs from 'fs';
import got from 'got';
import { shell } from './process.mjs';

export const downloadArtifact = async (sourcePath, targetFilename, context) =>
    shell(`curl --request GET http://${context.links.nexus.hostname}:${context.links.nexus.port}/repository/raw/${sourcePath} --verbose --header "Authorization: ${context.secrets.nexus.authorizationHeader()}" > ${targetFilename}`)
// new Promise(async (resolve, fail) => {
// const response = got.stream(
//     `http://${context.links.nexus.hostname}:${context.links.nexus.port}/repository/raw/${sourcePath}`,
//     {
//         headers: {
//             Authorization: context.secrets.nexus.authorizationHeader()
//         }
//     })

// response.on('response', rsp => {
//     if (rsp.statusCode !== 200) {
//         fail(rsp.statusCode)
//     }
// })
// response.on('downloadProgress', r => { })
// response.on('uploadProgress', r => { })
// response.on('retry', r => { })
// response.on('redirect', r => { })

// fs.openSync(path.resolve(targetFilename), 'w');
// const file = fs.createWriteStream(path.resolve(targetFilename))
// file.on('close', () => { resolve(targetFilename) })
// file.on('open', () => { })
// file.on('ready', () => { })
// response.pipe(file)
// })

export const uploadArtifact = async (sourcePath, context) => new Promise((resolve, fail) => {
    fs.createReadStream(step.with.path).pipe(http.request({
        ...context.links.nexus,
        headers: {
            Authorization: context.secrets.nexus.authorizationHeader()
        },
        path: `/repository/raw/${context.owner}/${context.repository}/${sourcePath}`,
        method: 'PUT',
    }, response => {
        response.on('close', () => { console.debug("close"); });
        response.on('data', (chunk) => { console.debug("data", chunk); });
        response.on('end', () => { console.debug("end"); });
        response.on('error', (err) => { console.debug("error", err); fail(err); });
        response.on('pause', () => { console.debug("pause"); });
        response.on('readable', () => { console.debug("readable"); response.statusCode === 201 ? resolve(response.statusCode) : fail(response.statusCode); });
        response.on('resume', () => { console.debug("resume"); });
    }));
})