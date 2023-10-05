# Use the official OpenJDK base image for Java 11
FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container at /app
COPY target/movement-account.jar ./app.jar

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "./app.jar"]