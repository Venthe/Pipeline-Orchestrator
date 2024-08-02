package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive._1.context.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.StepsContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.TimeoutContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.EnvironmentContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.Optional;

@SuppressWarnings("ALL")
public class NormalJobContext extends CommonJobContext {
    private final String runsOn;
    private final Optional<JobEnvironmentContext> environment;
    private final Optional<JobOutputsContext> outputs;
    private final Optional<EnvironmentContext> environmentVariables;
    private final Optional<StepsContext> steps;
    private final int timeoutMinutes;
    private final boolean continueOnError;
    private final Optional<ContainerContext> container;
    private final Optional<ServicesContext> services;


    public NormalJobContext(String jobId, JsonNode _root) {
        super(jobId, _root);
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        runsOn = RunsOnContext.ensure(root.get("runsOn"));
        environment = JobEnvironmentContext.create(root.get("environment"));
        outputs = JobOutputsContext.create(root.get("outputs"));
        environmentVariables = EnvironmentContext.create(root.get("env"));
        steps = StepsContext.create(root.get("steps"));
        timeoutMinutes = TimeoutContext.create(root.get("timeoutMinutes")).orElse(15);
        continueOnError = ContinueOnErrorContext.create(root.get("continueOnError")).orElse(false);
        container = ContainerContext.create(root.get("container"));
        services = ServicesContext.create(root.get("services"));
    }
}
