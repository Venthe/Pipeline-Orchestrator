/*
package eu.venthe.pipeline.orchestrator.modules.automation.workflows;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution._archive.Workflow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WorkflowTest {

    @Nested
    class Pin {
        @Test
        void workflowIsPinnedByDefault() {
            var workflow = new Workflow();

            Assertions.assertThat(workflow.isPinned()).isFalse();
        }

        @Test
        void unpinnedWorkflowCanBePinned() {
            var workflow = new Workflow();
            workflow.unpin();

            workflow.pin();
            Assertions.assertThat(workflow.isPinned()).isTrue();
        }

        @Test
        void pinnedWorkflowCanBePinned() {
            var workflow = new Workflow();
            workflow.pin();

            workflow.pin();
            Assertions.assertThat(workflow.isPinned()).isTrue();
        }

        @Test
        void unpinnedWorkflowCanBeUnpinned() {
            var workflow = new Workflow();
            workflow.unpin();

            workflow.unpin();
            Assertions.assertThat(workflow.isPinned()).isFalse();
        }

        @Test
        void pinnedWorkflowCanBeUnpinned() {
            var workflow = new Workflow();
            workflow.pin();

            workflow.unpin();
            Assertions.assertThat(workflow.isPinned()).isFalse();
        }
    }
}
*/
