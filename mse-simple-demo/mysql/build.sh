#!/bin/sh
set -e

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}demo-mysql:2.0.3-dubbo-2.5.x

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}demo-mysql:2.0.3-dubbo-2.5.x
fi

