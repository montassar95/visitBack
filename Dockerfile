 

## Stage 1: Build the application
#FROM maven:3.6.3-openjdk-8-slim AS build
#WORKDIR /app
#COPY . /app
#RUN mvn clean package

## Stage 2: Create the final image
#FROM openjdk:8-jre-alpine
#WORKDIR /app
#COPY --from=build /app/target/visitBack.jar /app/visitBack.jar
#EXPOSE 8080
#CMD ["java",j "-jar", "visitBack.jar"]




# Docker Build Stage
FROM maven:3.6.3-jdk-11-alpine AS build


# Copy folder in docker
WORKDIR /opt/app

COPY ./ /opt/app
RUN mvn clean install -DskipTests


# Run spring boot in Docker
FROM openjdk:11-jdk-alpine

COPY --from=build /opt/app/target/*.jar app.jar

ENV PORT 8081
EXPOSE $PORT

ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","app.jar"]



 