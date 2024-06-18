package eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.context.job_execution.contexts;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.sameKey;
import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.toMap;

/**
 * The secrets context contains the names and values of secrets that are available to a workflow run. The secrets
 * context is not available for composite actions due to security reasons. If you want to pass a secret to a composite
 * action, you need to do it explicitly as an input. For more information about secrets, see "Using secrets in GitHub
 * Actions."
 * <p>
 * GITHUB_TOKEN is a secret that is automatically created for every workflow run, and is always included in the secrets
 * context. For more information, see "Automatic token authentication."
 */
@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
public class SecretsContext {
    private static final String TOKEN_KEY = "GITHUB_TOKEN";

    /**
     * This context is the same for each job in a workflow run. You can access this context from any step in a job. This
     * object contains all the properties listed below.
     * <p>
     * GITHUB_TOKEN: Automatically created token for each workflow run. For more information, see "Automatic token
     * authentication."
     */
    @Singular
    @JsonAnyGetter
    private final Map<String, String> secrets = new HashMap<>();

    public SecretsContext(Map<String, String> secrets) {
        if (secrets.get(TOKEN_KEY) == null) {
            throw new UnsupportedOperationException();
        }

        this.secrets.putAll(secrets);
    }

    @JsonCreator
    public SecretsContext(JsonNode _root) {
        this(ContextUtilities.validateIsObjectNode(_root).properties().stream()
                .map(sameKey(ContextUtilities.Text::ensure))
                .collect(toMap())
        );
    }

    public static SecretsContext ensure(JsonNode root) {
        return ContextUtilities.ensure(root, SecretsContext::new);
    }
}
