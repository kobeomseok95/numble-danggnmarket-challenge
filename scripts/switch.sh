#!/usr/bin/env bash

# switch.sh
# nginx 연결 설정 스위치

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"

    SERVICE_IP=$(hostname -I | awk '{print $1}')
    echo "> IP 확인 : $SERVICE_IP"
    # nginx와 연결한 주소 생성
    # | sudo tee ~ : 앞에서 넘긴 문장을 service-url.inc에 덮어씀
    echo "set \$service_url http://${SERVICE_IP}:${IDLE_PORT};" | sudo tee /home/ec2-user/app/nginx/service-url.inc

    echo "> 엔진엑스 Reload"
    docker-compose -f /home/ec2-user/app/nginx/docker-compose-nginx.yml up --build -d
}
