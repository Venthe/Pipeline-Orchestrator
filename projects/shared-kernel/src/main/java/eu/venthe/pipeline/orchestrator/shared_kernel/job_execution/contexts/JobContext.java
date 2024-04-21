package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * The job context contains information about the currently running job.
 * <p>
 * This context changes for each job in a workflow run. You can access this context from any step in a job. This object
 * contains all the properties listed below.
 */
@AllArgsConstructor
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

    public static JobContext ensure(JsonNode job) {
        throw new UnsupportedOperationException();
    }

    @AllArgsConstructor
    public static class Container {
        /**
         * The ID of the container.
         */
        private final String id;
        /**
         * The ID of the container network. The runner creates the network used by all containers in a job.
         */
        private final String network;
    }

    @AllArgsConstructor
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
        private final Map<Integer, Integer> ports;
    }
}
