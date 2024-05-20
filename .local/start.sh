#!/usr/bin/env bash

# TODO: Email (mailcow?)
# TODO: PGPool
# TODO: Replicated gerrit?
docker compose \
  --env-file=./.env \
  --file=./openldap.docker-compose.yml \
  --file=./gerrit.docker-compose.yml \
  up \
  --detach

#  --file=./kafka.docker-compose.yml \
#  --file=./keycloak.docker-compose.yml \
#  --file=./nexus.docker-compose.yml \
#  --file=./pgadmin.docker-compose.yml \
#  --file=./vault.docker-compose.yml \
