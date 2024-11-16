# Use an official Java runtime as a base image
FROM openjdk:21
  
  # Set the working directory
WORKDIR /app
  
  # Copy the Spring Boot jar file into the container
COPY build/libs/product-api.jar app.jar
  
  # Expose port 8080 (or your application's port)
EXPOSE 8080
  
  # Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=local"]
