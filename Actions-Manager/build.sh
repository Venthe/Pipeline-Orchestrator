#!/usr/bin/env bash

. ./.env

DOCKER_BUILDKIT=0 docker build --progress=plain `pwd`/runner --tag="${BUILD_NAME}" $@