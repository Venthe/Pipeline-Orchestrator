import { Outlet } from "react-router-dom";
import { ApplicationContext } from "./common/applicationContext";
import { Navigation } from "./common/navigation";
import "./layout.css"
import { Notifications } from "./common/notifications";
import { Modals } from "./common/modals";

export const RootLayout = () => (
    <ApplicationContext>
        <div id="application-root">
        <header>
            <Navigation></Navigation>
        </header>
        <main style={{padding: "0 1rem"}}>
            <Outlet />
        </main>
        <Notifications />
        <Modals />
        </div>
    </ApplicationContext>
)