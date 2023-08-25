FROM openjdk:17.0.2-jdk
VOLUME /tmp
COPY target/*.jar stockonfo.jar
ENTRYPOINT ["java","-jar","/stockinfo.jar"]