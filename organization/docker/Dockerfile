#FROM openjdk:8-jdk-alpine
##RUN apk update && apk upgrade && apk netcat-openbsd
#COPY @jarName@ @jarName@
#CMD java -jar @jarName@
#EXPOSE 8080

FROM openjdk:8-jdk-alpine
#VOLUME /tmp
ADD target/organization-0.1.0.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar