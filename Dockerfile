FROM openjdk:17.0.2-jdk
VOLUME /tmp
COPY target/*.jar asyncapidatamanager.jar
ENTRYPOINT ["java","-jar","/asyncapidatamanager.jar"]