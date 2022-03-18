# build.sh 이후 도커 이미지 만들기 '도커허브계정명/앱이름:버전'

IMAGE_ID=$(docker images | grep my_pms | gawk '{ print $3 }')
[[ ! -z ${IMAGE_ID} ]] && docker rmi ${IMAGE_ID}

IMAGE=mkeasy/my_pms:v0.1 
docker build -f Dockerfile -t ${IMAGE} .
