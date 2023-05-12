FROM amazoncorretto:17
#FROM openjdk:11-jdk
MAINTAINER myNAME
WORKDIR /app
COPY target/app-runnable.jar /app/app-runnable.jar
ADD pom.xml /app/pom.xml
RUN echo "some text" > my.txt
CMD ["java","-jar","app-runnable.jar"]
