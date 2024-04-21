package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * The secrets context contains the names and values of secrets that are available to a workflow run. The secrets
 * context is not available for composite actions due to security reasons. If you want to pass a secret to a composite
 * action, you need to do it explicitly as an input. For more information about secrets, see "Using secrets in GitHub
 * Actions."
 * <p>
 * GITHUB_TOKEN is a secret that is automatically created for every workflow run, and is always included in the secrets
 * context. For more information, see "Automatic token authentication."
 */
@AllArgsConstructor
public class SecretsContext {
    /**
     * This context is the same for each job in a workflow run. You can access this context from any step in a job. This
     * object contains all the properties listed below.
     * <p>
     * GITHUB_TOKEN: Automatically created token for each workflow run. For more information, see "Automatic token
     * authentication."
     */
    private final Map<String, String> secrets;

    public static SecretsContext ensure(JsonNode secrets) {
        throw new UnsupportedOperationException();
    }
}
