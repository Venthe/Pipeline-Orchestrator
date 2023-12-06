import dedent from "dedent"

export const toPlantUml = ({ nodes, edges }: { edges: [string, string][], nodes: string[] }) => {
    const alias = (s: string) => s.replaceAll("@", "_").replaceAll("/", "__")

    const nodes_ = nodes.map(n => `component "${n}" <<S>> as "${alias(n)}"{\n}`).join("\n")
    const edges_ = edges.map(e => `"${alias(e[0])}" -u-> "${alias(e[1])}"`).join("\n")

    const body = dedent`@startuml
    ${nodes_}

    ${edges_}
    @enduml`
    return body
}

export const render = async body => await fetch('http://localhost:8000/plantuml/utxt', { method: 'POST', body: body }).then(r => r.text()).catch(console.error)