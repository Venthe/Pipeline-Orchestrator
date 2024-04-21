package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

/**
 * The runner context contains information about the runner that is executing the current job.
 * <p>
 * This context changes for each job in a workflow run. This object contains all the properties listed below.
 */
@AllArgsConstructor
public class RunnerContext {
    /**
     * The name of the runner executing the job. This name may not be unique in a workflow run as runners at the
     * repository and organization levels could use the same name.
     */
    private final String name;
    /**
     * The operating system of the runner executing the job. Possible values are Linux, Windows, or macOS.
     */
    private final String os;
    /**
     * The architecture of the runner executing the job. Possible values are X86, X64, ARM, or ARM64.
     */
    private final String arch;
    /**
     * The path to a temporary directory on the runner. This directory is emptied at the beginning and end of each job.
     * Note that files will not be removed if the runner's user account does not have permission to delete them.
     */
    private final String temp;
    /**
     * The path to the directory containing preinstalled tools for GitHub-hosted runners. For more information, see
     * "Using GitHub-hosted runners".
     */
    private final String toolCache;
    /**
     * This is set only if debug logging is enabled, and always has the value of 1. It can be useful as an indicator to
     * enable additional debugging or verbose logging in your own job steps.
     */
    private final String debug;


    public static RunnerContext ensure(JsonNode runner) {
        throw new UnsupportedOperationException();
    }
}
