FROM openjdk:19-alpine3.16
MAINTAINER suminksudhi
COPY target/product-service-1.0.0.jar product-service.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker", "/product-service.jar"]