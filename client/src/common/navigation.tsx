import { Tab, TabList } from "@fluentui/react-tabs";
import { useNavigate } from "react-router-dom";
import { navigation as projectsNavigation } from "../projects/routing";

export const Navigation = () => {
    const navigate = useNavigate();

    const data = [
        {label: "Home", path: "/"},
        projectsNavigation
    ].map((e, i) => ({...e, index: i}))
    
    const tabs = data.map((e) => <Tab key={e.index} value={e.index}>{e.label}</Tab>)

    return (
        <TabList defaultSelectedValue={data[0]?.index} onTabSelect={(e, d) => {
            const target = data.filter(el => el.index === d.value).map(el => el.path)[0]
            if(target) navigate(target)
        }}>
            {tabs}
        </TabList>
    );
}