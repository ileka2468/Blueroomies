# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app


COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project files
COPY src ./src

# Build app
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the JAR file from the build folder
COPY --from=build /app/target/*.jar rfa-0.2.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "rfa-0.2.jar"]
