import {step} from "@pipeline/core";
import {ActionStepDefinition} from "@pipeline/types";
import {CommonWith} from "./types";

type With = {
    restoreKeys: string[]
    failOnCacheMiss?: boolean
} & CommonWith;

export const run = async () => {
    const _with: With = (step as ActionStepDefinition<With>).with ?? ({} as With)
    console.log("CACHE: Save", _with, step)
};