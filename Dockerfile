# Multi-stage build for Journal Intime Application

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install required packages for JavaFX
RUN apk add --no-cache \
    fontconfig \
    ttf-dejavu \
    gtk+3.0 \
    mesa-gl \
    && rm -rf /var/cache/apk/*

# Copy the built jar from build stage
COPY --from=build /app/target/journal-intime-1.0.0.jar app.jar

# Create directory for data
RUN mkdir -p /app/data/lucene/indexes

# Expose port (if REST API is enabled)
EXPOSE 8080

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
