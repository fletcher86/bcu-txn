# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-oracle

# Set the working directory in the container
WORKDIR /app

# Copy the build artifacts from the host to the container
COPY build/libs/bcu-txn-1.0.0-SNAPSHOT.jar /app/bcu-txn-1.0.0.jar

# Expose the port your application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/bcu-txn-1.0.0.jar"]