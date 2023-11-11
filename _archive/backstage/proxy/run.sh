#!/usr/bin/env bash

IMG=`docker build -q -f ./gerrit-proxy.Dockerfile .`
echo "! ${IMG}"
docker run -it --rm -p '7443:443' ${IMG}