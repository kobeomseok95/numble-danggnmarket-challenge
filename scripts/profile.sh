#!/usr/bin/env bash

function find_idle_profile()
{
    # curl 결과로 연결할 서비스 결정
    HTTP_CODE=$(curl -k -s -o /dev/null -w "%{http_code}" https://danggn/health)

    if [ ${HTTP_CODE} -ge 400 ] || [ ${HTTP_CODE} -eq 000 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=prod1
    else
        CURRENT_PROFILE=$(curl -k -s https://danggn/health)
    fi

    # IDLE_PROFILE : nginx와 연결되지 않은 profile
    if [ ${CURRENT_PROFILE} == prod1 ]
    then
      IDLE_PROFILE=prod2
    else
      IDLE_PROFILE=prod1
    fi

    # bash script는 값의 반환이 안된다.
    # echo로 결과 출력 후, 그 값을 잡아서 사용한다.
    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == prod1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}
