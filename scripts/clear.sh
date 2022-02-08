#!/usr/bin/env bash

echo "> nginx 이미지 제거"
docker rmi -f $(docker images --filter "dangling=true" -q --no-trunc)
