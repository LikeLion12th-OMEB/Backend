FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp

# 컨텍스트 내의 build/libs에서 JAR 파일을 복사합니다
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
