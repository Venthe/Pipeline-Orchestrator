export type WorkflowTriggerEventType = "workflow_dispatch"

export interface WorkflowTriggerEvent {
    type: WorkflowTriggerEventType
}

export interface WorkflowDispatchEvent extends WorkflowTriggerEvent {
    type: "workflow_dispatch"
    inputs: {[key: string]: string} | undefined
    workflow: string
    sender: Sender
    repository: Repository
    ref: string
    organization?: Organization
    installation?: Installation
    enterprise?: Enterprise
}

export interface Sender {}
export interface Enterprise {}
export interface Installation {}
export interface Organization {}
export interface Repository {}
export interface Sender {}