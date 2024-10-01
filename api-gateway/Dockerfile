# 빌드 단계
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# 실행 단계
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]