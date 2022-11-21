import { render } from "./template.mjs";
import fs from 'fs';
import yaml from 'js-yaml'
import { context } from '../actions/context.mjs'

export const loadSteps = () => {
    const untemplatedYamlFile = fs.readFileSync(`${process.env.RUNNER_METADATA_DIRECTORY}/steps.yaml`, 'utf8');

    const { env } = yaml.load(untemplatedYamlFile)
    context["env"] = env;

    const nunjucksSub = render(untemplatedYamlFile, context);

    const { steps } = yaml.load(nunjucksSub)
    context["steps"] = steps

    return steps
}