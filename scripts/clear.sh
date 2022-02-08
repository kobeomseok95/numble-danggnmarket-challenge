#!/usr/bin/env bash

function docker_image_clear() {
  echo "> nginx 이미지 제거"
  docker rmi -f $(docker images --filter "dangling=true" -q --no-trunc)
}
