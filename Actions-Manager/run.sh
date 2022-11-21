#!/usr/bin/env bash

. ./.env

# set -x

./build-manager.sh

docker run \
    --rm \
    --volume `pwd`/runner:/runner/cache \
    --volume `pwd`/steps.yaml:/runner/metadata/steps.yaml \
    --volume `pwd`/manager/dist/index.mjs:/runner/index.mjs \
    --volume /var/run/docker.sock:/var/run/docker.sock \
    --interactive \
    --entrypoint 'bash' \
    --tty \
     $@ \
    "${BUILD_NAME}" \
     -c "node \"\${RUNNER_MANAGER_DIRECTORY}/index.mjs\""