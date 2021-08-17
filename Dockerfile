FROM java:8-jre-alpine
MAINTAINER flsh <franklions@sina.com>

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/local/micro-service/app.jar

#web 服务端口
EXPOSE 8080

WORKDIR /usr/local/micro-service/

ENV JAVA_OPTS -Xms512m -Xmx512m
ENV JAVA_ARGS --spring.profiles.active=pro

ENTRYPOINT /usr/bin/java $JAVA_OPTS -jar app.jar $JAVA_ARGS
