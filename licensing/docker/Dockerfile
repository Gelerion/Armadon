FROM openjdk:8-jdk-alpine

RUN addgroup -S gelerion && adduser -S -G gelerion gelerion

ENV APP_HOME /app
ENV JAVA_OPTS = "-Xmx200m -Djava.security.egd=file:/dev/./urandom"

WORKDIR ${APP_HOME}
COPY /licensing-server.jar ${APP_HOME}

EXPOSE 8082

USER gelerion
CMD ["java", "-jar", "licensing-server.jar"]