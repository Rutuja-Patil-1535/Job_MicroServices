# Use lightweight Java image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/gateway-0.0.1-SNAPSHOT.jar app.jar

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]