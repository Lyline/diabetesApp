FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=build/libs/diabetesMainApp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} diabetesMainApp.jar
ENTRYPOINT ["java","-jar","diabetesMainApp.jar"]
EXPOSE 8080