FROM openjdk:8-jdk-alpine

RUN addgroup -S gelerion && adduser -S -G gelerion gelerion

ENV APP_HOME /app
ENV JAVA_OPTS = "-Xmx200m -Djava.security.egd=file:/dev/./urandom"

WORKDIR ${APP_HOME}
COPY /gateway-server.jar ${APP_HOME}

EXPOSE 5555

USER gelerion
CMD ["java", "-jar", "gateway-server.jar"]