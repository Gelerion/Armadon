FROM openjdk:8-jre-alpine

RUN addgroup -S gelerion && adduser -S -G gelerion gelerion

ENV APP_HOME /app
ENV JAVA_OPTS = "-Xmx200m -Djava.security.egd=file:/dev/./urandom"

WORKDIR ${APP_HOME}
COPY /config-server.jar ${APP_HOME}

EXPOSE 8888

USER gelerion
#CMD ["java", "-jar", "$JAVA_OPTS", "config-server.jar"]
CMD ["java", "-jar", "config-server.jar"]