import { Tab, TabList } from "@fluentui/react-tabs";
import { Link, useNavigate } from "react-router-dom";
// import { navigation as projectsNavigation } from "../projects/routing";
import { Persona } from "@fluentui/react-persona";
import { Input } from "@fluentui/react-input";
import {
    Popover,
    PopoverSurface,
    PopoverTrigger,
    Text,
    Link as FLink
} from "@fluentui/react-components";
import * as catalog from '../catalog/routes';
import { DrawerBody, DrawerHeader, DrawerHeaderNavigation, DrawerHeaderTitle, InlineDrawer } from "@fluentui/react-components/unstable";

export const Navigation = () => {
    // TODO: Handle children
    const data: { path?: string, label: string, index: number, children?: unknown[] }[] = [
        { label: "Home", path: "/" },
        catalog.navigation,
        { label: "Pipelines", path: "/pipelines" },
        { label: "Projects", children: [{ label: "Issues" }, { label: "Pipelines" }] },
        { label: "APIs" },
        { label: "Docs" },
        { label: "Tech radar" },
        { label: "Explore" }
        // projectsNavigation
    ].map((e, i) => ({ ...e, index: i }))

    return (
        <InlineDrawer separator open={true}>
            <DrawerHeader>
                <DrawerHeaderTitle>Always open</DrawerHeaderTitle>
                <DrawerHeaderNavigation>
                    <Persona
                        name="Kevin Sturgis"
                        secondaryText="Administrator"
                        avatar={{
                            image: {
                                src: "https://res-1.cdn.office.net/files/fabric-cdn-prod_20230815.002/office-ui-fabric-react-assets/persona-male.png",
                            },
                        }}
                    />
                </DrawerHeaderNavigation>
            </DrawerHeader>

            <DrawerBody>
                {data.map(el => el.path ? <Link to={el.path}>{el.label}</Link> : <>{el.label}</>).map(el => <div>{el}</div>)}
            </DrawerBody>
        </InlineDrawer>
    );
}