FROM openjdk:18-slim
LABEL maintainer="Bogdanov Stepan"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]