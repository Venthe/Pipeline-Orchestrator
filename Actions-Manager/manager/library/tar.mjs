import { context } from "../actions/context.mjs";
import { shell } from "./process.mjs";

export const untar = async (file) => shell(`tar -xf ${file} -C ${context.binariesBase}`)