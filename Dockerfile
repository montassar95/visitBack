 

# Stage 1: Build the application
FROM maven:3.6.3-openjdk-8-slim AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

# Stage 2: Create the final image
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/target/visitBack.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","app.jar"]




 


#RUN mvn clean install -DskipTests
 