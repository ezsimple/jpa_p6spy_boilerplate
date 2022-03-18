FROM openjdk:8-jre-alpine

RUN apk update \
	&& apk add --no-cache procps ttf-freefont fontconfig vim iputils \
	&& rm -rf /var/lib/apt/lists/* \
	&& mkdir /usr/share/fonts/nanumfont \
	&& wget http://cdn.naver.com/naver/NanumFont/fontfiles/NanumFont_TTF_ALL.zip \
	&& unzip NanumFont_TTF_ALL.zip -d /usr/share/fonts/nanumfont \
	&& rm NanumFont_TTF_ALL.zip \
	&& fc-cache -f -v

VOLUME /upload
ADD ./build/libs/demo3-0.1.war app.war

ENV LANG=ko_KR.UTF-8 \
    SPRING_ACTIVE_PROFILE=local \
    JAVA_ARGS='-Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=${SPRING_ACTIVE_PROFILE}' \
    JAVA_OPTS='-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=2' \
    JAVA_DEBUG=''

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.war"]
