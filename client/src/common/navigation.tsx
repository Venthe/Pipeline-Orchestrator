import { Tab, TabList } from "@fluentui/react-tabs";
import { useNavigate } from "react-router-dom";
// import { navigation as projectsNavigation } from "../projects/routing";
import { Persona } from "@fluentui/react-persona";
import { Input } from "@fluentui/react-input";
import {
    Popover,
    PopoverSurface,
    PopoverTrigger,
    Text,
} from "@fluentui/react-components";
import * as catalog from '../catalog/routing';

export const Navigation = () => {
    const navigate = useNavigate();

    const data: { path?: string, label: string, index: number, children?: unknown[] }[] = [
        catalog.navigation,
        { label: "Home", path: "/" },
        { label: "Pipelines", path: "/pipelines" },
        { label: "Projects", children: [{ label: "Issues" }, { label: "Pipelines" }] },
        { label: "APIs" },
        { label: "Docs" },
        { label: "Tech radar" },
        { label: "Explore" }
        // projectsNavigation
    ].map((e, i) => ({ ...e, index: i }))

    const renderChildren = (children?: unknown[]) => children && children.length > 0 ? (
        <Popover>
            <PopoverTrigger>
                <span style={{ display: "flex", alignItems: "center" }}> ⬇</span>
            </PopoverTrigger>
            <PopoverSurface>
                <pre>{children.map(a => JSON.stringify(a, undefined, 2))}</pre>
            </PopoverSurface>
        </Popover>
    ) : <></>
    const tabs = data.map((e) => <><Tab key={e.index} disabled={!e.path} value={e.index}>{e.label}</Tab>{renderChildren(e.children)}</>)

    return (
        <div style={{ display: "flex", justifyContent: "space-between", padding: "0.5rem 0.5rem" }}>
            <div style={{ display: "flex" }}>
                <span style={{ display: "flex", alignItems: "center" }}>
                    🤡
                </span>
                <TabList defaultSelectedValue={data[0]?.index} onTabSelect={(e, d) => {
                    const target = data.filter(el => el.index === d.value).map(el => el.path ?? "/404")[0]
                    if (target) navigate(target)
                }}>
                    {tabs}
                </TabList>
            </div>
            <div style={{ display: "flex", flexDirection: "column", justifyContent: "center" }}>
                <Input size="small"
                    placeholder="Search"
                    aria-label="inline"
                    contentAfter={
                        <Text size={200}>🔎</Text>
                    } />
            </div>
            <Persona
                name="Kevin Sturgis"
                secondaryText="Administrator"
                avatar={{
                    image: {
                        src: "https://res-1.cdn.office.net/files/fabric-cdn-prod_20230815.002/office-ui-fabric-react-assets/persona-male.png",
                    },
                }}
            />
        </div>
    );
}