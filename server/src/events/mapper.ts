import { WorkflowTriggerEvent } from "./workflowTriggerEvents";

export const map = (event: WorkflowTriggerEvent) => {
    switch (event.type) {
        case "workflow_dispatch":
        default:
          console.error(`Event not handled ${event.type}`)
    }
}