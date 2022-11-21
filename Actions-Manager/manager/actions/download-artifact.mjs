import * as nexus from '../library/nexus.mjs'

export const downloadArtifact = async (step, context) =>
    nexus.downloadArtifact(step.with.filename, step.targetFilename ?? step.with.filename.split('/').pop(), context)
