export interface Component {
    id: string
    system: string
    organization?: string
    owner: string
    type: string
    lifecycle: string
    description: string
    tags: string[]
}

export class CatalogService {
    getComponents(): Component[] {
        return [
            {
                id: "podcast-api",
                system: "podcast",
                owner: "team-c",
                type: "service",
                lifecycle: " production",
                description: "Podcast API",
                tags: ["java", "podcast"]
            }
        ]
    }
    getCatalogElement() {
        return [
            {
                kind: "API",
                types: [
                    {key: "openapi"},
                    {key: "graphql"},
                    {key: "asyncapi"},
                    {key: "grpc"},
                    {key: "trpc"}
                ]
            },
            {
                kind: "Component",
                types: [
                    {key: "service"},
                    {key: "website"},
                    {key: "library"}
                ]
            },
            {
                kind: "Domain"
            },
            {
                kind: "Group",
                types: [
                    {key: "team"},
                    {key: "dub-department"},
                    {key: "department"},
                    {key: "organization"}
                ]
            },
            {
                kind: "Location",
                types: [
                    {key: "url"}
                ]
            },
            {
                kind: "Resource",
                types: [
                    {key: "database"}
                ]
            },
            {
                kind: "System"
            },
            {
                kind: "User"
            }
        ]
    }
}