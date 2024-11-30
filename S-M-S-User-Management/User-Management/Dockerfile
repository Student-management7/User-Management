# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .  # Copy the Maven project descriptor
COPY src ./src  # Copy the source code directory
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/Student-Management-System-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "app.jar"]
