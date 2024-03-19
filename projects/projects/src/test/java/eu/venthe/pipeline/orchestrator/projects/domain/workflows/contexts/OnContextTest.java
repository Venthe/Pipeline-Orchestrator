/*
package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.TriggerEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.TypeContext;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.Workflow;
import eu.venthe.pipeline.orchestrator.projects.domain.events.HandledEvent;
import eu.venthe.pipeline.orchestrator.projects.utilities.EventUtility;
import eu.venthe.pipeline.orchestrator.projects.utilities.YamlUtility;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.function.Consumer;

class OnContextTest {

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
                ref: main
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
                commits: []
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
                commits: []
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
                commits: []
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
                commits: []
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
                commits: []
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
                commits: []
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
                commits: []
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
                    sha: "123"
                    timestamp: 2011-12-03T10:15:30+01:00
                    author:
                      name: Test User
                    committer:
                      name: Test User
                    added:
                      - test.yaml
                    modified: []
                    removed: []
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
                    sha: "123"
                    timestamp: 2011-12-03T10:15:30+01:00
                    author:
                      name: Test User
                    committer:
                      name: Test User
                    added:
                      - test.yaml
                    modified: []
                    removed: []
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
                commits: []
                additionalProperties:
                  files:
                    "a.yaml":
                      status: A
                      lines_deleted: -5
                      size_delta: 5
                      size: 5
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
                ref: main
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

    private static HandledEvent getEvent(String value) {
        return getEvent(value, (c) -> {});
    }

    private static HandledEvent getEvent(String value, Consumer<ObjectNode> mutator) {
        ObjectNode event = (ObjectNode) YamlUtility.parseYaml(value);
        eu.venthe.pipeline.orchestrator.projects.api.version_control.common.EventType eventType = TypeContext.ensure(event);

        ObjectNode repository = YamlUtility.createObjectNode();
        repository.set("provider", YamlUtility.getNodeFactory().textNode("test"));
        repository.set("name", YamlUtility.getNodeFactory().textNode("Test-Repository"));
        repository.set("htmlUrl", YamlUtility.getNodeFactory().textNode("https://example.com/Test-Repository"));
        repository.set("getBlobsUrl", YamlUtility.getNodeFactory().textNode("https://example.com/Test-Repository/blobs/{path}"));
        repository.set("getBlobsUrl", YamlUtility.getNodeFactory().textNode("https://example.com/Test-Repository/blobs/{path}"));
        repository.set("createdAt", YamlUtility.getNodeFactory().textNode(OffsetDateTime.now().toString()));
        repository.set("updatedAt", YamlUtility.getNodeFactory().textNode(OffsetDateTime.now().toString()));
        repository.set("gitUrl", YamlUtility.getNodeFactory().textNode("git://https://example.com/Test-Repository"));
        repository.set("sshUrl", YamlUtility.getNodeFactory().textNode("ssh://https://example.com/Test-Repository"));
        repository.set("cloneUrl", YamlUtility.getNodeFactory().textNode("ssh://https://example.com/Test-Repository"));
        repository.set("defaultBranch", YamlUtility.getNodeFactory().textNode("main"));

        if (eventType.equals(eu.venthe.pipeline.orchestrator.projects.api.version_control.common.EventType.WORKFLOW_DISPATCH)) {
            event.set("repository", repository);
            event.set("workflow", YamlUtility.getNodeFactory().textNode(".pipeline/workflows/test.yaml"));

            event.set("ref", YamlUtility.getNodeFactory().textNode("main"));
        }

        if (eventType.equals(eu.venthe.pipeline.orchestrator.projects.api.version_control.common.EventType.PUSH)) {
            event.set("before", YamlUtility.getNodeFactory().textNode("123"));
            event.set("after", YamlUtility.getNodeFactory().textNode("321"));
            event.set("repository", repository);

            ObjectNode pusher = YamlUtility.createObjectNode();
            pusher.set("name", YamlUtility.getNodeFactory().textNode("Test user"));
            event.set("pusher", pusher);

            ObjectNode sender = YamlUtility.createObjectNode();
            sender.set("name", YamlUtility.getNodeFactory().textNode("Test user"));
            event.set("sender", sender);
        }

        ObjectNode actor = YamlUtility.createObjectNode();
        actor.set("login", YamlUtility.getNodeFactory().textNode("TestUser"));
        event.set("actor", actor);

        event.set("id", YamlUtility.getNodeFactory().textNode("a5b41ec4-c088-420a-93ae-8b0868b6e1b8"));

        mutator.accept(event);

        return EventUtility.eventMapper(new TriggerEvent(event));
    }
}
*/
