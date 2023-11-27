FROM "${RUNNER_BASE_IMAGE:-docker.io/library/ubuntu:23.10}"

RUN apt-get update \
    && apt-get install --assume-yes \
           software-properties-common \
           nodejs \
           unzip \
           vim \
           iptables \
           ca-certificates \
           curl \
           gnupg \
           lsb-release \
    && add-apt-repository ppa:git-core/ppa \
    && mkdir -p /etc/apt/keyrings \
    && curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg \
    && echo \
         "deb [arch=amd64 signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
         jammy stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null \
    && curl -fsSL https://deb.nodesource.com/gpgkey/nodesource-repo.gpg.key | gpg --dearmor -o /etc/apt/keyrings/nodesource.gpg \
    && echo "deb [signed-by=/etc/apt/keyrings/nodesource.gpg] https://deb.nodesource.com/node_20.x nodistro main" | tee /etc/apt/sources.list.d/nodesource.list \
    && apt-get update \
    && apt-get install --assume-yes docker-ce docker-ce-cli containerd.io docker-compose-plugin git build-essential zlib1g-dev libffi-dev libssl-dev libbz2-dev libreadline-dev libsqlite3-dev liblzma-dev nodejs \
    && apt-get clean

ADD "./runner/application/dist" "/runner"
ADD "./runner/application/runner.sh" "/runner/runner.sh"

WORKDIR "/workdir"

RUN mkdir -p \
    "/runner" \
    "/runner/actions" \
    "/runner/bin" \
    "/runner/cache" \
    "/runner/metadata" \
    "/runner/metadata/pipeline" \
    "/runner/metadata/env" \
    "/runner/metadata/secrets" \
    "/workdir"

ENTRYPOINT ["/runner/runner.sh"]
CMD ["run", "--enable-source-maps"]
