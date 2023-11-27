import os from 'os';
import fs from 'fs';
import path from 'path';

export const inTempDirectory = async (action: (directory: string) => Promise<void>, options?: {prefix?: string}) => {
    const temporaryDirectory = fs.mkdtempSync(path.join(os.tmpdir(), options?.prefix ?? "mantle"))
    await action?.(temporaryDirectory)
    fs.rmSync(temporaryDirectory, { recursive: true, force: true })
}