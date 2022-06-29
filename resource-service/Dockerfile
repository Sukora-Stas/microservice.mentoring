FROM openjdk:11-jdk-oracle
ARG JAR_FILE=build/libs/resource-service.jar
EXPOSE 8081
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]