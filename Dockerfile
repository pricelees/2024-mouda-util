# Use JDK Corretto 17 as the base image
FROM amazoncorretto:17.0.11

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file to the container
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=dev

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]