#!/bin/sh
export REGISTRY=${REGISTRY}

export appName=demo-mysql
export VERSION="${VERSION:-3.1.0}"

set -e

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}${appName}:${VERSION}

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}${appName}:${VERSION}
fi

