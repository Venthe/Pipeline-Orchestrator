import {spawn} from "child_process";
import * as clc from "cli-color";

const spawnProcess = (command: string, options: Options) => spawn(command, {shell: options.shell ?? true, ...(options.cwd ? {cwd: options.cwd} : {})});
const removeLastNewLine = (data) => data.replace(/\n+$/, "")

export function password(keyword) {
    return new RegExp(`(?<=${keyword}[:\\s=]+)[:\\w]+(?=.*)`, "gi")
}

export interface MappedChunk {
    chunk: string,
    date: string,
    streamType: StreamType
}

const unwrapChunkData = (mapFunction: (...any) => void) => (chunk: MappedChunk) => mapFunction(chunk.chunk);

function maskText(text, masks) {
    let result = text;
    masks?.forEach(m => {
        result = result.replace(m, "*****")
    })
    return result;
}

const prepareChunk: (any, StreamType, mask?: (RegExp | string)[]) => MappedChunk = (chunk, streamType, mask?: (RegExp | string)[]) => {
    return ({
        chunk: maskText(removeLastNewLine(chunk.toString()), mask),
        date: new Date().toISOString(),
        streamType
    });
}

export enum StreamType {
    STDERR = "stderr",
    STDOUT = "stdout"
}

export type StdioCallback = ({chunk, date, streamType}: MappedChunk) => void;

const chunkCallback = (streamType: StreamType, functions: (StdioCallback | undefined)[], mask?: (RegExp | string)[]) => (unparsedChunk: any) => {
    functions.filter(a => a !== undefined)
        .map(a => (a as StdioCallback))
        .forEach(fn => fn(prepareChunk(unparsedChunk, streamType, mask)))
}

const logDefaultFirstLine = (command: string) => console.log(`> ${command}`);

export type ShellCallbacks = { stderr?: StdioCallback, stdout?: StdioCallback };

type OutputLine = { stream: StreamType, line: string, time: string };
type Result = { code: number | null, error?: Error, output?: OutputLine[] };

function outputParser(chunk: MappedChunk): OutputLine {
    return {
        line: chunk.chunk,
        stream: chunk.streamType,
        time: chunk.date.toString()
    };
}

interface Options {
    callbacks?: ShellCallbacks
    silent?: boolean
    output?: boolean
    cwd?: string
    shell?: string
    mask?: (RegExp | string)[]
}

export const shell: (command: string | undefined, options?: Options) => Promise<Result>
    = async (command, {silent = false, output = true, shell = "bash", callbacks = {}, ...rest} = {}) =>
    new Promise<Result>((resolveSuccess, resolveFailure) => {
        if (command === undefined) {
            return {code: 0}
        }

        const options: Options = {silent, output, callbacks, shell, ...rest}

        if (!silent) {
            logDefaultFirstLine(maskText(command, rest.mask));
        }
        let error: Error | undefined = undefined;
        let _output: MappedChunk[] | undefined = output ? [] : undefined;

        const childProcess = spawnProcess(command, options);
        childProcess.stdout.on('data', chunkCallback(StreamType.STDOUT, [
            silent ? undefined : unwrapChunkData(console.log),
            output ? a => _output?.push(a) : undefined,
            callbacks?.stdout
        ], rest.mask));
        childProcess.stderr.on('data', chunkCallback(StreamType.STDERR, [
            silent ? undefined : unwrapChunkData(console.error),
            output ? a => _output?.push(a) : undefined,
            callbacks?.stderr
        ], rest.mask));
        childProcess.on('error', (e: Error) => error = e)
        childProcess.on('close', (code: number | null) => {
            const result = {
                code,
                ...(error || {}),
                ...(output === undefined ? {} : {output: _output?.map(outputParser).sort()})
            };
            if (code !== 0 || error != undefined) {
                resolveFailure(result)
            }
            resolveSuccess(result)
        });
    })

export const shellMany = async (commands: string[], options?: Options) => {
    for (const command of commands) {
        await shell(command, options);
    }
}
