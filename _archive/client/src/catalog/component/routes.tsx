import { PathRouteProps } from "react-router-dom";
import { OverviewPage } from "./overview/Page";
import { Pipelines } from "./pipelines/Pipelines";
import { Documentation } from "./documentation/Documentation";
import { Todos } from "./todos/Todos";

export const routes: (PathRouteProps & { label: string })[] = [
    { label: "Overview", path: "", element: <OverviewPage /> },
    { label: "Pipelines",  path: "pipelines", element: <Pipelines /> },
    { label: "Api",  path: "api", element: <Pipelines /> },
    { label: "Documentation",  path: "documentation", element: <Documentation /> },
    { label: "Todos",  path: "todos", element: <Todos /> },
]