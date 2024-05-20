#!/usr/bin/env bash

npx json-schema-faker-cli <(cat "./${1}.yaml" | yq --output-format=json) | yq --input-format=json
