# Build Stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /target/EffectiveApi-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]