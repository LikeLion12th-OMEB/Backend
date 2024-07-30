FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp

# 컨텍스트 내에서 build/libs 폴더의 모든 JAR 파일을 app.jar로 복사
COPY batch/build/libs/*.jar app.jar

# JAR 파일을 실행하기 위해 필요한 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
