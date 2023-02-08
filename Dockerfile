FROM openjdk:17-oracle
VOLUME /tmp
ARG JAR_FILE
ARG CONFIG_FOLDER
ADD ${JAR_FILE} app.jar
ADD ${CONFIG_FOLDER}/application-docker.properties application.properties

ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8081