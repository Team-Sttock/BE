# Use the official OpenJDK 11 base image
FROM openjdk:11-jre-slim

# Add a maintainer label to describe who is maintaining the image
LABEL maintainer="leeegyubin"

# Specify the directory inside the container where the app will reside
WORKDIR /app

# Add the JAR file from your target directory to the container
ARG JAR_FILE=build/libs/sttock-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Expose the port your Spring app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
