/*
package eu.venthe.pipeline.orchestrator.archive.core.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import eu.venthe.pipeline.orchestrator.archive.core.model.vo.ContinuousIntegrationEventStatus;
import eu.venthe.pipeline.orchestrator.archive.dependencies.JenkinsUtilities;
import eu.venthe.pipeline.orchestrator.archive.dependencies.gerrit_hook_binding.GerritApi;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpEntity;

@Document(collection = "events")
@TypeAlias("revision@v1")
public class RevisionEvent extends AbstractContinuousIntegrationEvent {
    @SuppressWarnings("unused")
    @PersistenceCreator
    protected RevisionEvent(String referenceId, String originId, ObjectNode event, ContinuousIntegrationEventStatus status) {
        super(referenceId, originId, event, status);
    }

    private RevisionEvent(ObjectNode event) {
        super(event);
    }

    private RevisionEvent(String originId,
                          ObjectNode event) {
        super(originId, event);
    }

    public static RevisionEvent create(ObjectNode event,
                                       ObjectMapper objectMapper,
                                       GerritApi gerritApi) {
        HttpEntity<?> traceId = gerritApi.generateTraceId();

        ObjectNode root = objectMapper.createObjectNode();

        root.set("type", new TextNode(typeProvider(event)));

        ObjectNode metadata = objectMapper.createObjectNode();
        TextNode repositoryNameNode = JenkinsUtilities.getByPath(event, "refUpdate.repository", JsonNode::asText).map(TextNode::new).orElseThrow();
        metadata.set("repositoryName", repositoryNameNode);
        TextNode revisionNode = JenkinsUtilities.getByPath(event, "refUpdate.newRev", JsonNode::asText).map(TextNode::new).orElseThrow();
        metadata.set("revision", revisionNode);
        metadata.set("branchName", JenkinsUtilities.getByPath(event, "refUpdate.refName", JsonNode::asText).map(TextNode::new).orElseThrow());
        root.set("metadata", metadata);

        ObjectNode additionalProperties = objectMapper.createObjectNode();
        additionalProperties.set("commit", gerritApi.getCommitForRepository(repositoryNameNode.textValue(), revisionNode.textValue(), traceId));
        additionalProperties.set("files", gerritApi.getCommitFilesForRepository(repositoryNameNode.textValue(), revisionNode.textValue(), traceId));
        root.set("additionalProperties", additionalProperties);

        return new RevisionEvent(root);
    }

    @Override
    public AbstractContinuousIntegrationEvent copyForRetrigger() {
        return new RevisionEvent(getReferenceId(), getEvent().deepCopy());
    }
}
*/
