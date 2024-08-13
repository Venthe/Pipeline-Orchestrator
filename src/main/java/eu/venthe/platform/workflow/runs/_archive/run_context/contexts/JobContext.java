package eu.venthe.platform.workflow.runs._archive.run_context.contexts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Map;
import java.util.Optional;

import static eu.venthe.platform.application.utilities.CollectionUtilities.sameKey;
import static eu.venthe.platform.application.utilities.CollectionUtilities.toMap;

/**
 * The job context contains information about the currently running job.
 * <p>
 * This context changes for each job in a workflow run. You can access this context from any step in a job. This object
 * contains all the properties listed below.
 */
@Getter
@ToString
@EqualsAndHashCode
public class JobContext {
    /**
     * Information about the job's container. For more information about containers, see "Workflow syntax for GitHub
     * Actions."
     */
    private final Container container;
    /**
     * The service containers created for a job. For more information about service containers, see "Workflow syntax for
     * GitHub Actions."
     */
    private final Map<String, Service> services;
    /**
     * The current status of the job. Possible values are success, failure, or cancelled.
     */
    private final String status;

    @JsonCreator
    public JobContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        status = ContextUtilities.Text.ensure(root.get("status"));
        services = ContextUtilities.validateIsObjectNode(root.get("services")).properties().stream()
                .map(sameKey(Service::ensure))
                .collect(toMap());
        container = Container.ensure(root.get("container"));
    }

    public static JobContext ensure(JsonNode job) {
        return ContextUtilities.ensure(job, JobContext::new);
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Container {
        /**
         * The ID of the container.
         */
        private final Optional<String> id;
        /**
         * The ID of the container network. The runner creates the network used by all containers in a job.
         */
        private final String network;

        private Container(JsonNode _root) {
            ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

            id = ContextUtilities.Text.create(root.get("id"));
            network = ContextUtilities.Text.ensure(root.get("network"));
        }

        public static Container ensure(JsonNode container) {
            return ContextUtilities.ensure(container, Container::new);
        }
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder
    public static class Service {
        /**
         * The ID of the service container.
         */
        private final String id;

        /**
         * The ID of the service container network. The runner creates the network used by all containers in a job.
         */
        private final String network;

        /**
         * The exposed ports of the service container.
         */
        @Singular
        private final Map<String, String> ports;

        private Service(JsonNode _root) {
            ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

            id = ContextUtilities.Text.ensure(root.get("id"));
            network = ContextUtilities.Text.ensure(root.get("network"));
            ports = ContextUtilities.validateIsObjectNode(root.get("ports"))
                    .properties().stream()
                    .map(sameKey(ContextUtilities.Text::ensure))
                    .collect(toMap());
        }

        public static Service ensure(JsonNode value) {
            return ContextUtilities.ensure(value, Service::new);
        }
    }
}
