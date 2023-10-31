// export interface WorkflowRun {
//     status: "queued" | "in-progress" | "waiting" | "neutral" | "success" | "completed" | "failed" | "action-required" | "cancelled" | "skipped" | "stale" | "timed-out"
//     runId: string
//     event: string
//     label: string
//     commitDetails: {
//         branchId: string
//     }
//     startDate: Date
//     endDate: Date
//     actorId: string
//     workflowFile: string
// };

// export class WorkflowsService {
//     listRuns(): WorkflowRun[] {
//         return [
//             {
//                 runId: "123",
//                 status: "completed",
//                 event: "On tag push",
//                 label: "Fix secret management",
//                 commitDetails: {
//                     branchId: "sdasdads"
//                 },
//                 actorId: "xyz",
//                 workflowFile: "assa",
//                 startDate: new Date(),
//                 endDate: new Date()
//             }
//         ]
//     }
// } 