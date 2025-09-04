# JDK 21 기반 이미지 사용
FROM eclipse-temurin:21-jdk AS runtime

# 작성자 정보
LABEL authors="kitri"

# 작업 디렉토리 설정
WORKDIR /app

# build/libs에 있는 JAR 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 애플리케이션 실행 (java -jar)
ENTRYPOINT ["java", "-jar", "app.jar"]
