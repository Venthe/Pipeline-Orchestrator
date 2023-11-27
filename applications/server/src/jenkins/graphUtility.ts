import Graph, { DirectedGraph } from "graphology";

// https://stackoverflow.com/questions/67337917/group-tasks-for-concurrent-processing-in-directed-acyclic-dependency-graph-using
export const topologicalGenerations = (graph: Graph): string[][] => {
    const groups: string[][] = []
    //The first group contains all vertices without incoming arcs
    let group: string[] = []

    Array.from(graph.nodes()).filter(node => graph.outDegree(node) == 0).forEach(node => group.push(node))
    // Next we construct all remaining groups. The group k+1 consists of al vertices without incoming arcs if we were
    // to remove all vertices in the previous group k from the graph.
    let result = true
    while (result) {
        groups.push([...group]);
        const nextGroup: string[] = []
        for (const task of group) {
            for (const nextTask of graph.neighbors(task)) {
                if (graph.outDegree(nextTask) == 1) nextGroup.push(nextTask);
            }
            graph.dropNode(task); //Removes a vertex and all its edges from the graph
        }
        group = nextGroup.sort();
        result = group.length > 0
    }

    return groups
}


type Jobs = {
    [key: string]: {
        needs?: string[];
    };
};

type Roots = string[]

export const buildDependencyTree = (jobs: Jobs, roots?: Roots): string[][] => {
    const graph = new DirectedGraph();

    if (!roots || roots.length === 0) {
        Object.keys(jobs).forEach(node => graph.addNode(node))

        Object.keys(jobs).map(key => ({ key, value: jobs[key] }))
            .filter(entry => !!entry.value.needs)
            .flatMap(entry =>
                entry.value.needs?.map(needs => ([entry.key, needs] as [string, string])) ?? []
            )
            .forEach(edge => graph.mergeDirectedEdge(...edge));
    } else {
        const nodes: string[] = []
        const edges: [string, string][] = []

        const toVerticies = (_roots: Roots) => Object.entries(jobs)
            .filter(en => _roots.includes(en[0]))
            .reduce((acc, val) => {
                acc[val[0]] = val[1].needs ?? []
                return acc
            }, {})

        const travel: (v: { [key: string]: string[] }) => void = (v) => {
            Object.entries(v).forEach(([name, deps]) => {
                nodes.push(name)
                deps.map(d => [name, d] as [string, string]).forEach(e => edges.push(e))
                travel(toVerticies(deps))
            })
        }

        travel(toVerticies(roots))

        new Set(nodes).forEach(node => graph.addNode(node))
        // TODO: Will fail on edge existing?
        edges.forEach(edge => graph.mergeDirectedEdge(...edge))
    }

    return topologicalGenerations(graph)
}