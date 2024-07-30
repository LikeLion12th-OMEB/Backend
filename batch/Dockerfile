# 기본 이미지로 OpenJDK 21을 사용
FROM eclipse-temurin:21-jdk-alpine

# 컨테이너 내의 임시 파일을 저장할 디렉토리 생성
VOLUME /tmp

# JAR 파일을 컨테이너 내의 /app.jar로 복사
COPY ./batch/build/libs/*.jar /app.jar
 
# JAR 파일을 실행하기 위한 기본 명령어 설정
ENTRYPOINT ["java", "-jar", "/app.jar"]
