#!/usr/bin/env bash

set -o xtrace

#        --verbose \
function upload() {
    curl \
        --fail \
        --user admin:secret \
        --upload-file \
        "${1}" \
        "http://localhost:8081/repository/raw/${@:2}"
}

upload ./zulu17.38.21-ca-jdk17.0.5-linux_x64.tar.gz java/jdk/zulu17.38.21-ca-jdk17.0.5-linux_x64.tar.gz
upload ./gradle-7.6-all.zip gradle/gradle-7.6-all.zip
upload ./kubectl-linux-adm64-v1.26.0 kubernetes/kubectl-linux-adm64-v1.26.0
upload ./docker-20.10.22.tgz ./docker/docker-20.10.22.tgz
