FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

WORKDIR /app
COPY . .
COPY src/main/resources/security/keystore.p12 keystore.p12

ENTRYPOINT ["java","-jar","/app.jar"]