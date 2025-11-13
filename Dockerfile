
# Use a lightweight JDK 21 image
FROM maven:3.9-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
