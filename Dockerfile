FROM openjdk:17-jdk-slim
LABEL authors="leonardofurlanis-ieg"

WORKDIR /app

COPY target/meu-projeto.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
