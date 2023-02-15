#!/bin/sh

cd "$(dirname "$0")"

docker build . -t ${REGISTRY}spring-cloud-b:1.2.0-eureka

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}spring-cloud-b:1.2.0-eureka
fi
