FROM openjdk:8-jdk-alpine

RUN addgroup -S gelerion && adduser -S -G gelerion gelerion

ENV APP_HOME /app

WORKDIR ${APP_HOME}
COPY /registry-server.jar ${APP_HOME}

EXPOSE 8761

USER gelerion
CMD ["java", "-jar", "registry-server.jar"]