FROM openjdk:17.0.2-jdk
VOLUME /tmp
COPY target/*.jar stockinfo.jar
ENTRYPOINT ["java","-jar","/stockinfo.jar"]