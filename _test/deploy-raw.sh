#!/usr/bin/env bash

set -o xtrace

function upload() {
    curl \
        --fail \
        --verbose \
        --user admin:secret \
        --upload-file \
        "${1}" \
        "http://localhost:8081/repository/raw/${@:2}"
}

upload ./zulu17.38.21-ca-jdk17.0.5-linux_x64.tar.gz java/jdk/zulu17.38.21-ca-jdk17.0.5-linux_x64.tar.gz
upload ./gradle-7.5.1-all.zip gradle/gradle-7.5.1-all.zip