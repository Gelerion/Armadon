FROM openjdk:8-jre-alpine

RUN addgroup -S gelerion && adduser -S -G gelerion gelerion

ENV APP_HOME /app
ENV JAVA_OPTS = "-Xmx200m -Djava.security.egd=file:/dev/./urandom"

WORKDIR ${APP_HOME}
COPY /auth-server.jar ${APP_HOME}

EXPOSE 8901

USER gelerion
CMD ["java", "-jar", "auth-server.jar"]