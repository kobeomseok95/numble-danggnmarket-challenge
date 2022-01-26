#!/usr/bin/env bash

# stop.sh
# 서버 중단을 위한 스크립트

ABSPATH=$(readlink -f $0)
# ABSDIR : 현재 stop.sh 파일 위치의 경로
ABSDIR=$(dirname $ABSPATH)
# import profile.sh
source ${ABSDIR}/profile.sh

IDLE_PROFILE=$(find_idle_profile)

echo "> $IDLE_PROFILE 의 구동중인 애플리케이션 컨테이너 ID 확인"
IDLE_CONTAINER_ID=$(docker ps -aq --filter name=${IDLE_PROFILE})

if [ -z ${IDLE_CONTAINER_ID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> $IDLE_CONTAINER_ID stop"
  docker rm -f ${IDLE_CONTAINER_ID}
  sleep 5
fi
