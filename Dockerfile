FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080

# Permite definir o profile via variável de ambiente externa
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-local}

ENTRYPOINT ["java", "-jar", "app.jar"]
