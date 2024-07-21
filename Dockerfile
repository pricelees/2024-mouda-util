# Use JDK Corretto 17 as the base image
FROM amazoncorretto:17.0.12

# Set the working directory in the container
WORKDIR /app

# Port to expose
EXPOSE 8080

# Copy the JAR file to the container
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar

# Datasource environment variables
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD

ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}

# Run the JAR
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","-Dspring.datasource.url=${SRPING_DATASOURCE_URL}", "-Dspring.datasource.username=${SRPING_DATASOURCE_USERNAME}", "-Dspring.datasource.password=${SRPING_DATASOURCE_PASSWORD}", "/app.jar"]
