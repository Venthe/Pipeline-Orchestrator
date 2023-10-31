import { Outlet } from "react-router-dom";
import { Catalog } from "./Catalog";
import { Component } from "./component/Component";

export const navigation = {
    label: "Catalog",
    path: "/catalog"
}

export const routes = {
    path: "/catalog",
    element: <Outlet />,
    children: [
        { path: "", element: <Catalog /> },
        {
            path: ":organizationId",
            element: <Outlet />,
            children: [
                { path: "component/:componentId", element: <Component /> }
            ]
        }
    ]
}