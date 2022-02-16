FROM adoptopenjdk:11-jdk-hotspot

ARG JAR_FILE=build/libs/challenge-0.0.1-SNAPSHOT.jar
ARG JASPYT_PASSWORD=""

COPY ${JAR_FILE} app.jar

EXPOSE 5000

ENV JASPYT_PASSWORD=$JASPYT_PASSWORD

ENTRYPOINT ["java", "-jar", "-Djasypt.encryptor.password=${JASPYT_PASSWORD}", "-Dspring.profiles.active=ebprod","/app.jar"]
