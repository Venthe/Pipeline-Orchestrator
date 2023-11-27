import path from 'path'

const config = {
    url: {
        gitiles: "http://host.docker.internal:8010/plugins/gitiles"
    }
}

export const showFile = async (props: { projectName: string, ref: string, filePath: string, request?: Partial<Request>, format?: 'TEXT'}): Promise<string> => {
    const url = new URL(path.join(config.url.gitiles, props.projectName, "+", props.ref, props.filePath))

    url.searchParams.append("format", props.format ?? 'TEXT')

    const requestConfiguration = props.request ?? {}
    const staticConfiguration = {
        headers: new Headers({
            "Accept": "text/plain",
        })
    }

    const request = new Request(url, { ...requestConfiguration, ...staticConfiguration })

    return fetch(request)
        .then(e => e.text())
        .then(e => atob(e))
}