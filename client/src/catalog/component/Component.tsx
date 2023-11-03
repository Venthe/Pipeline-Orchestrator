import { TabList, Tab } from "@fluentui/react-components"
import { Outlet, useLocation, useNavigate } from "react-router-dom"
import { routes } from "./routes"

export const Component = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const mapPathToId = (path) => {
        if (path === undefined) throw new Error()

        return path.length === 0 ? "default" : path
    }

    const selectedId = routes.filter(el => location.pathname.includes(mapPathToId(el.path)))
      .map(el => el.path)
      .map(mapPathToId)[0];
    console.log(location, routes.filter(el => location.pathname.includes(mapPathToId(el.path)))
    .map(el => el.path)
    .map(mapPathToId))

    return <>
        <div>
            <TabList selectedValue={selectedId} onTabSelect={(event, data) => navigate(data.value as string)}>
                {routes.map(r => <Tab id={mapPathToId(r.path)} value={r.path}>{r.label}</Tab>)}
            </TabList>
        </div>
        <Outlet />
    </>
}