#!/usr/bin/env bash

set -o errexit

npx ajv-cli compile -s ./workflow-trigger-event.jsonschema.yaml
npx json-schema-faker-cli <(npx js-yaml ./workflow-trigger-event.jsonschema.yaml) | yq --prettyPrint 'sort_keys(..)'
yq ./workflow-trigger-event.jsonschema.yaml -ojson > schema.json