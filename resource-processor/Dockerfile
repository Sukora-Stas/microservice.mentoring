FROM openjdk:11-jdk-oracle
ARG JAR_FILE=build/libs/resource-processor.jar
EXPOSE 8083
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]