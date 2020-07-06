FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY ./target/api-1.0.0.jar api.jar
COPY ./docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod 755 /docker-entrypoint.sh

RUN touch /api.jar && mkdir -p /config

WORKDIR /

EXPOSE 8080

ENTRYPOINT [ "/docker-entrypoint.sh" ]

CMD [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/api.jar" ]