FROM openjdk:19-alpine3.16
MAINTAINER suminksudhi
COPY target/review-service-1.0.0.jar review-service.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/review-service.jar"]