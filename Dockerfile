# Build stage
FROM maven:3.8.6-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY src/main/resources/Projeto-vaga-Ambiental.xlsx src/main/resources/
RUN mvn clean package -DskipTests

# Application stage
FROM selenium/standalone-chrome:latest
WORKDIR /app

# Copy the application JAR file from the build stage
COPY --from=build /app/target/*.jar application.jar

# Copy the xlsx file
COPY src/main/resources/Projeto-vaga-Ambiental.xlsx /app/resources/

# Set the path to chromedriver (if needed, though standalone-chrome should handle this)
ENV PATH="/usr/local/bin/chromedriver:${PATH}"

ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/app/application.jar"]
