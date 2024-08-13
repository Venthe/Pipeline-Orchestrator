package eu.venthe.pipeline.runner.runner_engine.template.model;

import java.net.URL;
import java.util.Map;

public record RunnerContext(JobRunRequestContext jobRunRequestContext,
                            URL systemApiUrl,
                            RunCallbackToken runCallbackToken) {

    /**
     * runner.name	string	The name of the runner executing the job. This name may not be unique in a workflow run as
     * runners at the repository and organization levels could use the same name. runner.os	string	The operating system
     * of the runner executing the job. Possible values are Linux, Windows, or macOS. runner.arch	string	The
     * architecture of the runner executing the job. Possible values are X86, X64, ARM, or ARM64.
     * runner.debug	string	This is set only if debug logging is enabled, and always has the value of 1. It can be useful
     * as an indicator to enable additional debugging or verbose logging in your own job steps.
     * runner.environment	string	The environment of the runner executing the job. Possible values are: github-hosted for
     * GitHub-hosted runners provided by GitHub, and self-hosted for self-hosted runners configured by the repository
     * owner.
     */
    public record Runner() {
    }

    public record Secrets(Map<String, String> secrets) {
    }
}
