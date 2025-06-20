# Use Maven with Java 17 pre-installed
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Set working directory inside container
WORKDIR /app

# Copy all files into the image
COPY . .

# Build the project using Maven
RUN mvn clean install

# -----------------------------------
# Runtime container (slim, only JRE)
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the jar file from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
