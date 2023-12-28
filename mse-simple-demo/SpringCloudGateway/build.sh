#!/bin/sh
export REGISTRY=${REGISTRY}

export appName=spring-cloud-gateway
export VERSION="${VERSION:-3.0.6-semeru-8}"

set -e

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}${appName}:${VERSION}

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}${appName}:${VERSION}
fi
