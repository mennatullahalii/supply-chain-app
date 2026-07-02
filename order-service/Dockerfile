# ==========================================
# STAGE 1: Build the application
# ==========================================
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle executable and configuration files first
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy the actual Kotlin source code
COPY src src

# Run the build command (skipping tests for speed right now)
RUN ./gradlew build -x test

# ==========================================
# STAGE 2: Run the application
# ==========================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy ONLY the compiled .jar file from Stage 1
COPY --from=builder /app/build/libs/*.jar app.jar

# Tell Docker what port this container listens on
EXPOSE 8080

# The command to start the app
ENTRYPOINT ["java", "-jar", "app.jar"]