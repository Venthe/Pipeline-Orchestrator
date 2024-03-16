package eu.venthe.pipeline.orchestrator.projects.api;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ProjectsCommandService {

    void add(CreateProjectSpecification newProjectDto);
    void update(ProjectId projectId);
    void archive(ProjectId projectId);
    void makePublic(ProjectId projectId);
    void executeManualWorkflow(ProjectId projectId, String workflowName);
    void handleEvent(ProjectId projectId, ObjectNode event);
}
