import { context } from "../actions/context.mjs";
import { shell } from "./process.mjs";

export const unzip = async (file) => shell(`unzip -u ${file} -d ${context.binariesBase}`)