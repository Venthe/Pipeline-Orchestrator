import { Projects } from "./page";
import { Workflow } from "./workflows/workflow";
import { Workflows } from "./workflows/workflows";

export const navigation = {
    path: "/projects", label: "Projects"
}
export const routing = {
    path: "/projects",
    element: <Projects />,
    children: [
        {
            path: "",
            element: <Workflows />,
        },
        {
            path: "runs/:runId",
            element: <Workflow />,
        }
    ]
}