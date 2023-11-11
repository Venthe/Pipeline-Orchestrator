import { Card, CardFooter, CardHeader, CardPreview, Tag, TagGroup } from "@fluentui/react-components"

export const About = () => {
    return (
        <Card>
            <CardHeader
                header={<div style={{ width: "100%", display: "flex", justifyContent: "space-between" }}>
                    <div>About</div>
                    <div style={{ display: "flex" }}>
                        <div>Refresh</div>
                        <div>Edit</div>
                    </div>
                </div>}
                description={<>
                    <div>View source</div>
                    <div>View techdocs</div></>}
            />
            
            <div>
                <div><span>Description: Artist lookup</span></div>
                <div>
                    <span>Owner: Team-a</span>
                    <span>System: artist-engagement-portal</span>
                    <span>Type: service</span>
                    <span>Lifecycle: experimental</span>
                    <span>Tags: <TagGroup><Tag>java</Tag><Tag>data</Tag></TagGroup></span>
                </div>
            </div>

            <CardFooter />
        </Card>
    )
}