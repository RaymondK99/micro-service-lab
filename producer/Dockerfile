#FROM openjdk:13-alpine
FROM bellsoft/liberica-openjdk-alpine

EXPOSE 8080

## Add nice to haves
RUN apk add nano
RUN apk add bash
RUN apk add curl

COPY target/producer-0.0.1-SNAPSHOT.jar  /opt/
WORKDIR /opt


ENV JAVA_OPTS    -Xmx256m
CMD bash -c "java $JAVA_OPTS -jar producer-0.0.1-SNAPSHOT.jar"
