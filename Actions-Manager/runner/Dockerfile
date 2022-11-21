FROM "${RUNNER_IMAGE:-docker.io/library/ubuntu:22.04}"

RUN apt-get update \
    && apt-get install --assume-yes curl software-properties-common apt-utils \
    && curl --fail --silent --show-error --location "${NODE_VERSION:-https://deb.nodesource.com/setup_19.x}" | bash - \
    && add-apt-repository ppa:git-core/ppa \
    && apt-get update \
    && apt-get install --assume-yes git nodejs \
    && apt-get clean

ENV RUNNER_CACHE_DIRECTORY="/runner/cache" \
    RUNNER_MANAGER_DIRECTORY="/runner" \
    RUNNER_METADATA_DIRECTORY="/runner/metadata" \
    RUNNER_WORKDIR="/workdir"
WORKDIR "$RUNNER_WORKDIR"
VOLUME ["$RUNNER_CACHE_DIRECTORY", "$RUNNER_METADATA_DIRECTORY"]
ENTRYPOINT ["node", "$RUNNER_MANAGER_DIRECTORY"]