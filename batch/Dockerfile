FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=batch/build/libs/*.jar
COPY ${JAR_FILE} batch/app.jar
ENTRYPOINT ["java","-jar","batch/app.jar"]