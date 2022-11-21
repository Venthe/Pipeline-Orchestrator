import { unzip } from '../library/zip.mjs';
import { context } from './context.mjs';
import { downloadArtifact } from '../library/nexus.mjs';

export const setupGradle = async (step, ctx) => {
    const filename = await downloadArtifact('gradle/gradle-7.5.1-all.zip', `${context.binariesBase}/gradle.zip`, ctx)
    await unzip(filename)
    context.additionalPath.push(`${context.binariesBase}/gradle-7.5.1/bin`)
}