#!/usr/bin/env bash

# start.sh
# 서버 구동을 위한 스크립트

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app

#echo "> Build 파일 복사"
#echo "> cp $REPOSITORY/deploy/*.jar $REPOSITORY/"
#
#cp $REPOSITORY/deloy/*.jar $REPOSITORY/

echo "> 위치 이동"
cd /home/ec2-user/app
pwd

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr *.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)
IDLE_PORT=$(find_idle_port)

echo "> docker build and run jar name = $JAR_NAME , profile = $IDLE_PROFILE "
docker build -t app:latest .
docker run --env PROFILE=$IDLE_PROFILE -dit -p $IDLE_PORT:$IDLE_PORT --name $IDLE_PROFILE app
