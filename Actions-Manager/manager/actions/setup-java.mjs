import { downloadArtifact } from '../library/nexus.mjs';
import { shell } from '../library/process.mjs';
import { untar } from '../library/tar.mjs';
import { context } from './context.mjs';

export const setupJava = async (step, ctx) => {
    const filename = await downloadArtifact('java/jdk/zulu17.38.21-ca-jdk17.0.5-linux_x64.tar.gz', `${context.binariesBase}/java.tar.gz`, ctx)
    await untar(filename)
    context.additionalPath.push(`${context.binariesBase}/zulu17.38.21-ca-jdk17.0.5-linux_x64/bin`)
    context.env["JAVA_HOME"] = `${context.binariesBase}/zulu17.38.21-ca-jdk17.0.5-linux_x64`
}