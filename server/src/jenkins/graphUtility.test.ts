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
    ])("build dependency tree", () => {
        // given
        const jobs = {
            d: { needs: ["a", "b"] },
            b: { needs: ["a"] },
            c: { needs: ["a"] },
            a: {}
        }

        // when
        const result = buildDependencyTree(jobs)

        // then
        expect(result).toEqual([["a"], ["b", "c"], ["d"]])
    })
})