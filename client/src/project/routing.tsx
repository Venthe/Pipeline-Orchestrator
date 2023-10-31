// export const navigation = {
//     path: "/projects", label: "Projects"
// }

// export const routing = [
//     {
//         path: "/projects",
//         element: <Outlet />,
//         children: [
//             {
//                 path: "",
//                 element: <ProjectsList />,
//             },
//             {
//                 path: "runs/:runId",
//                 element: <Workflow />,
//             },
//             {
//                 path: ":projectId",
//                 element: <Project />,
//             }
//         ]
//     },
//     {
//         path: "/pipelines",
//         element: <Outlet />,
//         children: [
//             {
//                 path: "",
//                 element: <WorkflowList />,
//             },
//             {
//                 path: "workflows/:fileName",
//                 element: <WorkflowList />,
//             },
//             {
//                 path: "runs/:runId",
//                 element: <RunSummary />,
//                 children: [
//                     {
//                         path: "workflow",
//                         element: <Workflow />,
//                     },
//                     {
//                         path: "usage",
//                         element: <Usage />,
//                     },
//                     {
//                         path: "job/:jobId",
//                         element: <Job />,
//                     }
//                 ]
//             }
//         ]
//     }
// ]