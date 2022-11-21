
import * as nexus from '../library/nexus.mjs'

export const uploadArtifact = async (step, context) =>
    nexus.uploadArtifact(step.with.name, context)