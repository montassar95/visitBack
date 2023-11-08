FROM openjdk:8
EXPOSE 8080
ADD target/visitBack.jar visitBack.jar 
ENTRYPOINT ["java","-jar","/visitBack.jar"]