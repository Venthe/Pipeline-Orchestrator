#!/usr/bin/env bash

cat contexts.schema.yaml \
    | yq --no-colors --output-format=json eval \
    | generate-json /dev/stdin \
    | yq eval --prettyPrint --output-format=yaml \
    $@