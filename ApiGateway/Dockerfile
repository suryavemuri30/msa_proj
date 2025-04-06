FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/ApiGateway-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app/app.jar"]