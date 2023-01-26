FROM openjdk:18-jdk-alpine3.14
ARG JAR_FILE=/target/file-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]