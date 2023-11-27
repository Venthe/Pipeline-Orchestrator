import { buildDependencyTree } from "./graphUtility"

describe("Dependency tree", () => {
    test.each([
        {
            jobs: {
                d: { needs: ["a", "b"] },
                b: { needs: ["a"] },
                c: { needs: ["a"] },
                a: {}
            },
            result: [["a"], ["b", "c"], ["d"]]
        },
        {
            jobs: {
                a: {}
            },
            result: [["a"]]
        },
        {
            jobs: {
                d: {needs:["a"]},
                a: {},
                b: {needs: ["a"]},
                c: {needs: ["a"]}
            },
            result: [["a"], ["b", "c", "d"]]
        },
        {
            jobs: {
                d: {needs: ["a"]},
                a: {},
                b: {needs: ["a", "d"]},
                c: {needs: ["a", "d"]}
            },
            result: [["a"], ["d"], ["b", "c"]]
        },
        {
            jobs: {
                d: {needs: ["a"]},
                a: {},
                b: {needs: ["d"]},
                c: {needs: ["b"]},
            },
            result: [["a"], ["d"], ["b"], ["c"]]
        }
    ].map(a=>({...a, name: JSON.stringify(a.result)})))("build dependency tree $name", ({jobs, result: e}) => {
        // when
        const result = buildDependencyTree(jobs as any)

        // then
        expect(result).toEqual(e)
    })
})