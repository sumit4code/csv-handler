# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="sumit4job@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Set the current working directory inside the image
WORKDIR /app

RUN mkdir -p /app/fileloader /app/sourceDir /app/logs /app/backup


# Make port 8081 available to the world outside this container
EXPOSE 8081

# The application's jar file
ARG JAR_FILE=target/csv-handler.jar

# Add the application's jar to the container
ADD ${JAR_FILE} csv-handler.jar

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","/app/csv-handler.jar"]