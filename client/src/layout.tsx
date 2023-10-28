import { Outlet } from "react-router-dom";
import { ApplicationContext } from "./common/applicationContext";
import { Navigation } from "./common/navigation";
import "./layout.css"
import { Notifications } from "./common/notifications";
import { Modals } from "./common/modals";

export const Root = () => (
    <ApplicationContext>
        <div id="application-root">
        <header>
            <Navigation></Navigation>
        </header>
        <main>
            <Outlet />
        </main>
        <Notifications />
        <Modals />
        </div>
    </ApplicationContext>
)