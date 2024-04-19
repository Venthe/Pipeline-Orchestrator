package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs.*;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;

import java.util.Optional;

@SuppressWarnings("ALL")
public class NormalJobContext {
    private final String name;
    private final Optional<NeedsContext> needs;
    private final Optional<PermissionsContext> permissions;
    private final String runsOn;
    private final Optional<JobEnvironmentContext> environment;
    private final Optional<JobOutputsContext> outputs;
    private final Optional<EnvironmentContext> environmentVariables;
    private final Optional<DefaultsContext> defaults;
    private final Optional<IfContext> _if;
    private final Optional<StepsContext> steps;
    private final int timeoutMinutes;
    private final boolean continueOnError;
    private final Optional<ContainerContext> container;
    private final Optional<ServicesContext> services;
    private final Optional<ConcurrencyContext> concurrency;


    public NormalJobContext(String jobId, JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        name = JobNameContext.create(root.get("name")).orElse(jobId);
        needs = NeedsContext.create(root.get("needs"));
        permissions = PermissionsContext.create(root.get("permissions"));
        runsOn = RunsOnContext.ensure(root.get("runsOn"));
        environment = JobEnvironmentContext.create(root.get("environment"));
        outputs = JobOutputsContext.create(root.get("outputs"));
        environmentVariables = EnvironmentContext.create(root.get("env"));
        defaults = DefaultsContext.create(root.get("defaults"));
        _if = IfContext.create(root.get("if"));
        steps = StepsContext.create(root.get("steps"));
        timeoutMinutes = TimeoutContext.create(root.get("timeoutMinutes")).orElse(15);
        continueOnError = ContinueOnErrorContext.create(root.get("continueOnError")).orElse(false);
        container = ContainerContext.create(root.get("container"));
        services = ServicesContext.create(root.get("services"));
        concurrency = ConcurrencyContext.create(root.get("concurrency"));
    }
}
