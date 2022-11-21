import { spawn } from 'node:child_process';
import clc from "cli-color";

export const shell = async (command) => new Promise((resolveSuccess, resolveFailure) => {
    const childProcess = spawn(`${command}`, { shell: true });
    const enrichExitCode = code => ({ code, reason: `child process exited with code ${code}` });

    const output = [];
    const removeLastNewLine = (data) => data.replace(/\n+$/, "")
    const addLine = (type, line) => output.push({ type, line: removeLastNewLine(line) })
    const addStdErr = (line) => addLine("stderr", line)
    const addStdOut = (line) => addLine("stdout", line)
    const returnResult = (code) => ({ code, output })

    childProcess.stdout.on('data', (data) => {
        console.log(`${removeLastNewLine(data.toString())}`);
        addStdErr(data.toString())
    });
    childProcess.stderr.on('data', (data) => {
        console.error(`${clc.red(removeLastNewLine(data.toString()))}`);
        addStdOut(data.toString())
    });
    childProcess.on('error', code => resolveFailure(enrichExitCode(code)))

    childProcess.on('close', (code) => {
        if (code !== 0) {
            resolveFailure(enrichExitCode(code))
        }
        resolveSuccess(returnResult(code))

    });
})