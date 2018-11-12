FROM openjdk:8-jdk-alpine

RUN addgroup -S gelerion && adduser -S -G gelerion gelerion

ENV APP_HOME /app

WORKDIR ${APP_HOME}
COPY /registry-server.jar ${APP_HOME}
COPY /wait-for.sh ${APP_HOME}

EXPOSE 8761

RUN chmod +x ${APP_HOME}/wait-for.sh
RUN chown gelerion:gelerion ${APP_HOME}/wait-for.sh

USER gelerion
CMD ["java", "-jar", "registry-server.jar"]