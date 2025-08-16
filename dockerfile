FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY target/rinha-augusto-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]