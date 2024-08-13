package eu.venthe.platform.workflow.runs._archive.run_context.contexts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

/**
 * The runner context contains information about the runner that is executing the current job.
 * <p>
 * This context changes for each job in a workflow run. This object contains all the properties listed below.
 */
@SuppressWarnings("ALL")
@Getter
@ToString
@EqualsAndHashCode
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
    private final Optional<String> debug;

    @JsonCreator
    protected RunnerContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        name = ContextUtilities.Text.ensure(root.get("name"));
        os = ContextUtilities.Text.ensure(root.get("os"));
        arch = ContextUtilities.Text.ensure(root.get("arch"));
        temp = ContextUtilities.Text.ensure(root.get("temp"));
        toolCache = ContextUtilities.Text.ensure(root.get("tool_cache"));
        debug = ContextUtilities.Text.create(root.get("debug"));
    }


    public static RunnerContext ensure(JsonNode root) {
        return ContextUtilities.ensure(root, RunnerContext::new);
    }
}
