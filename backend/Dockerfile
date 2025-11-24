# ===============================
# Stage 1: Build the Application
# ===============================
FROM maven:3.9-amazoncorretto-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies first
# This step is cached by Docker unless pom.xml changes
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
# We skip tests here to speed up the deployment process on Render
RUN mvn clean package -DskipTests

# ===============================
# Stage 2: Run the Application
# ===============================
FROM amazoncorretto:17-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
# Note: We use a wildcard *.jar to avoid hardcoding the version number
COPY --from=build /app/target/*.jar app.jar

# Spring Boot usually runs on 8080, but Render assigns a dynamic port.
# We don't hardcode the port in EXPOSE, but strictly for documentation:
EXPOSE 3333

# Run the application
# We configure the server port to use Render's PORT environment variable
ENTRYPOINT ["java", "-jar", "app.jar"]
