# build.sh 이후 도커 이미지 만들기 '도커허브계정명/앱이름:버전'
IMAGE=mkeasy/my_pms:v0.1 
docker build -f Dockerfile -t ${IMAGE} .
docker images | grep "${IMAGE}"
