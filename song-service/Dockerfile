FROM openjdk:11-jdk-oracle
ARG JAR_FILE=build/libs/song-service.jar
EXPOSE 8082
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","--enable-preview","-jar","/app.jar"]