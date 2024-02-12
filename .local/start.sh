#!/usr/bin/env bash

# TODO: Email (mailcow?)
# TODO: PGPool
# TODO: Replicated gerrit?
docker compose \
  --env-file=./.env \
  --file=./wiremock.docker-compose.yml \
  up \
  --detach