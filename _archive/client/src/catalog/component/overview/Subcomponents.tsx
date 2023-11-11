import { Card, CardHeader } from "@fluentui/react-components";

export const Components = () => (
    <>
    <Card>
        <CardHeader header={"Has subcomponents"}/>

        <div>Name, owner, type, lifecycle, description</div>
        <div>No subcomponent is part of this component</div>
        <div>Learn how to change this</div>
    </Card>
    </>
)