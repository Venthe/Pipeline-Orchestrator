package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.api.PullRequestEvent;
import eu.venthe.pipeline.orchestrator.projects.api.PushEvent;
import eu.venthe.pipeline.orchestrator.projects.api.WorkflowDispatchEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.projects.domain.events.PullRequestEventWrapper;
import eu.venthe.pipeline.orchestrator.projects.domain.events.PushEventWrapper;
import eu.venthe.pipeline.orchestrator.projects.domain.events.WorkflowDispatchEventWrapper;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.Workflow;
import eu.venthe.pipeline.orchestrator.projects.utilities.YamlUtility;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

class OnContextTest {
    private final ObjectMapper objectMapper = YamlUtility.OBJECT_MAPPER;

    @Test
    void notNull1() {
        // given & when
        ThrowableAssert.ThrowingCallable action = () -> getWorkflow(null);

        // then
        Assertions.assertThatThrownBy(action).hasMessage("Workflow should not be null");
    }

    @Test
    void notNull2() {
        // given
        var workflow = getWorkflow("""
                on: ~
                jobs: {}
                """);

        // when
        ThrowableAssert.ThrowingCallable action = () -> workflow.on(null);

        // then
        Assertions.assertThatThrownBy(action).hasMessage("Input should not be null");
    }

    @Test
    void noOnProperty() {
        // given & when
        ThrowableAssert.ThrowingCallable action = () -> getWorkflow("""
                any: 1
                """);
        ;

        // then
        Assertions.assertThatThrownBy(action).hasMessage("There is no \"on\" property, this workflow will never run");
    }

    @Nested
    class EventType {
        @Test
        void singleEvent() {
            // given
            var input = getEvent("""
                    type: pull_request
                    action: opened
                    """);
            var workflow = getWorkflow("""
                    on: pull_request
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void doesNotMatch() {
            // given
            var input = getEvent("""
                    type: pull_request
                    action: opened
                    """);
            var workflow = getWorkflow("""
                    on: workflow_dispatch
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isFalse();
        }

        @Test
        void multipleEvents() {
            // given
            var input = getEvent("""
                    type: pull_request
                    action: opened
                    """);
            var workflow = getWorkflow("""
                    on: [pull_request]
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void emptyType() {
            // given
            var input = getEvent("""
                    type: workflow_dispatch
                    inputs: {}
                    """);
            var workflow = getWorkflow("""
                    on:
                      workflow_dispatch: {}
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }
    }

    @Nested
    class BranchesMatch {
        @Test
        void simpleMatch() {
            // given
            var input = getEvent("""
                    type: push
                    ref: main
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        branches:
                          - main
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void simpleDoesNotMatch() {
            // given
            var input = getEvent("""
                    type: push
                    ref: main
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        branchesIgnore:
                          - main
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isFalse();
        }

        @Test
        void wildcardMatch() {
            // given
            var input = getEvent("""
                    type: push
                    ref: main
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        branches:
                          - ma*
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void branchDoesNotMatch() {
            // given
            var input = getEvent("""
                    type: push
                    ref: main
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        branches:
                          - lorem
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isFalse();
        }

        @Test
        void branchIsOneOf() {
            // given
            var input = getEvent("""
                    type: push
                    ref: main
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        branches:
                          - lorem
                          - main
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void branchIsOneOfWithWildcard_1() {
            // given
            var input = getEvent("""
                    type: push
                    ref: refs/heads/main
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        branches:
                          - '**main'
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void activityTypbranchIsOneOfWithWildcard_2() {
            // given
            var input = getEvent("""
                    type: push
                    ref: main
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        branches:
                          - '**main'
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }
    }

    @Nested
    class PathsMatch {

        @Test
        void activityType_push5() {
            // given
            var input = getEvent("""
                    type: push
                    ref: main
                    commits:
                      - message: Abc
                        timestamp: 2011-12-03T10:15:30+01:00
                        added:
                          - test.yaml
                        tree_id: "123"
                        author:
                          name: Test User
                        committer:
                          name: Test User
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        paths:
                          - '**.yaml'
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void activityType_push5_2() {
            // given
            var input = getEvent("""
                    type: push
                    ref: main
                    commits:
                      - message: Abc
                        timestamp: 2011-12-03T10:15:30+01:00
                        added:
                          - test.yaml
                        tree_id: "123"
                        author:
                          name: Test User
                        committer:
                          name: Test User
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        pathsIgnore:
                          - '**.yaml'
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isFalse();
        }

        @Test
        void activityType_push6() {
            // given
            var input = getEvent("""
                    type: push
                    ref: main
                    commits:
                      - message: Abc
                        timestamp: 2011-12-03T10:15:30+01:00
                        added:
                          - a.yaml
                        tree_id: "123"
                        author:
                          name: Test User
                        committer:
                          name: Test User
                    """);
            var workflow = getWorkflow("""
                    on:
                      push:
                        paths:
                          - '**.other'
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isFalse();
        }
    }

    @Nested
    class Inputs {

        @Test
        void noInputNoneRequired() {
            // given
            var input = getEvent("""
                    type: workflow_dispatch
                    inputs: {}
                    """);
            var workflow = getWorkflow("""
                    on:
                      workflow_dispatch:
                        inputs:
                          username:
                            description: 'A username passed from the caller workflow'
                            default: 'john-doe'
                            required: false
                            type: string
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void noInputAllRequired() {
            // given
            var input = getEvent("""
                    type: workflow_dispatch
                    inputs: {}
                    """);
            var workflow = getWorkflow("""
                    on:
                      workflow_dispatch:
                        inputs:
                          username:
                            description: 'A username passed from the caller workflow'
                            default: 'john-doe'
                            required: true
                            type: string
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isFalse();
        }

        @Test
        void hasInputMatchingType_1() {
            // given
            var input = getEvent("""
                    type: workflow_dispatch
                    inputs:
                      username: Test
                    """);
            var workflow = getWorkflow("""
                    on:
                      workflow_dispatch:
                        inputs:
                          username:
                            description: 'A username passed from the caller workflow'
                            default: 'john-doe'
                            required: true
                            type: string
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void hasInputMatchingType_2() {
            // given
            var input = getEvent("""
                    type: workflow_dispatch
                    inputs:
                      username: 1
                    """);
            var workflow = getWorkflow("""
                    on:
                      workflow_dispatch:
                        inputs:
                          username:
                            description: 'A username passed from the caller workflow'
                            default: 2
                            required: true
                            type: number
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void hasInputMatchingType_3() {
            // given
            var input = getEvent("""
                    type: workflow_dispatch
                    inputs:
                      username: true
                    """);
            var workflow = getWorkflow("""
                    on:
                      workflow_dispatch:
                        inputs:
                          username:
                            description: 'A username passed from the caller workflow'
                            default: false
                            required: true
                            type: boolean
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void hasInputNotMatchingType() {
            // given
            var input = getEvent("""
                    type: workflow_dispatch
                    inputs:
                      username: Xyz
                    """);
            var workflow = getWorkflow("""
                    on:
                      workflow_dispatch:
                        inputs:
                          username:
                            description: 'A username passed from the caller workflow'
                            default: false
                            required: true
                            type: boolean
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isFalse();
        }
    }

    @Nested
    class Types {
        @Test
        void singleMatch() {
            // given
            var input = getEvent("""
                    type: pull_request
                    action: opened
                    """);
            var workflow = getWorkflow("""
                    on:
                      pull_request:
                        types: opened
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void singleDoesNotMatch() {
            // given
            var input = getEvent("""
                    type: pull_request
                    action: closed
                    """);
            var workflow = getWorkflow("""
                    on:
                      pull_request:
                        types: opened
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isFalse();
        }

        @Test
        void arrayMatch() {
            // given
            var input = getEvent("""
                    type: pull_request
                    action: opened
                    """);
            var workflow = getWorkflow("""
                    on:
                      pull_request:
                        types: [opened]
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        void arrayDoesNotMatch() {
            // given
            var input = getEvent("""
                    type: pull_request
                    action: closed
                    """);
            var workflow = getWorkflow("""
                    on:
                      pull_request:
                        types: [opened]
                    jobs: {}
                    """);

            // when
            boolean result = workflow.on(input);

            // then
            Assertions.assertThat(result).isFalse();
        }
    }

    private static Workflow getWorkflow(String value) {
        if (value == null) {
            return new Workflow(null, null);
        }
        ObjectNode workflow = value.isBlank() ? YamlUtility.getNodeFactory().objectNode() : (ObjectNode) YamlUtility.parseYaml(value);
        return new Workflow(workflow, new Workflow.WorkflowRef("Test-Repository", "main", ".pipeline/workflows/test.yaml", "sha"));
    }

    @SneakyThrows
    private <T extends Event> EventWrapper<?> getEvent(String value) {
        ObjectNode eventTree = (ObjectNode) objectMapper.readTree(value);
        eventTree.set("id", objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString()));

        return switch (eventTree.get("type").asText().toLowerCase(Locale.ROOT)) {
            case "workflow_dispatch" -> mapWorkflowDispatch(eventTree);
            case "push" -> mapPush(eventTree);
            case "pull_request" -> mapPullRequest(eventTree);
            default -> throw new UnsupportedOperationException();
        };
    }

    private PushEventWrapper mapPush(ObjectNode eventTree) throws JsonProcessingException {
        return new PushEventWrapper(objectMapper.treeToValue(eventTree, PushEvent.class));
    }

    private WorkflowDispatchEventWrapper mapWorkflowDispatch(ObjectNode eventTree) throws JsonProcessingException {
        TextNode ref = Optional.ofNullable((TextNode) eventTree.get("ref")).filter(JsonNode::isTextual).orElse(objectMapper.getNodeFactory().textNode("main"));
        eventTree.set("ref", ref);
        return new WorkflowDispatchEventWrapper(objectMapper.treeToValue(eventTree, WorkflowDispatchEvent.class));
    }

    private PullRequestEventWrapper mapPullRequest(ObjectNode eventTree) throws JsonProcessingException {
        ObjectNode pullRequest = Optional.ofNullable((ObjectNode) eventTree.get("pull_request")).orElse(objectMapper.createObjectNode());
        ObjectNode base = Optional.ofNullable((ObjectNode) pullRequest.get("base")).orElse(objectMapper.createObjectNode());
        TextNode ref = Optional.ofNullable((TextNode) base.get("ref")).filter(JsonNode::isTextual).orElse(objectMapper.getNodeFactory().textNode("main"));
        base.set("ref", ref);
        pullRequest.set("base", base);
        eventTree.set("pull_request", pullRequest);
        return new PullRequestEventWrapper(objectMapper.treeToValue(eventTree, PullRequestEvent.class));
    }
}
