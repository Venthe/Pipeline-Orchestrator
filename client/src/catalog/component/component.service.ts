export class ComponentService {
    getComponent(options: { organizationId?: string, componentId: string }) {
        return {
            kind: "Component",
            tags: [
                "java",
                "data"
            ],
            links: [
                {
                    url: "https://example.com/user",
                    title: "Examples Users",
                    icon: "user"
                },
                {
                    url: "https://example.com/group",
                    title: "Example Group",
                    icon: "group"
                },
                {
                    url: "https://example.com/cloud",
                    title: "Link with Cloud Icon",
                    icon: "cloud"
                },
                {
                    url: "https://example.com/dashboard",
                    title: "Dashboard",
                    icon: "dashboard"
                },
                {
                    url: "https://example.com/help",
                    title: "Support",
                    icon: "help"
                },
                {
                    url: "https://example.com/web",
                    title: "Website",
                    icon: "web"
                },
                {
                    url: "https://example.com/alert",
                    title: "Alerts",
                    icon: "alert"
                }
            ],
            annotations: {
                "backstage.io/linguist": "https://github.com/backstage/backstage/tree/master/plugins/playlist"
            },
            spec: {
                name: "artist-lookup",
                description: "Artist Lookup",
                type: "service",
                lifecycle: "experimental",
                owner: "team-a",
                system: "artist-engagement-portal",
                dependsOn: [
                    "resource:artists-db"
                ],
                apiConsumedBy: [
                    "component:www-artist"
                ]
            }
        }
    }
}