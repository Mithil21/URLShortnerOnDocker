# Stage 1: Build the Spring Boot application (using a multi-stage build for smaller final image)
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copy Maven/Gradle wrapper and relevant build files for caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
# For Gradle users, uncomment these and comment Maven lines:
# COPY gradlew .
# COPY gradle gradle
# COPY build.gradle settings.gradle .

# Download dependencies (Maven example)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN ./mvnw clean install -DskipTests

# Stage 2: Create the final lean image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
# For Gradle users:
# COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port your Spring Boot app listens on (default 8080)
EXPOSE 8080

# Command to run your application when the container starts
ENTRYPOINT ["java", "-jar", "/app/app.jar"]