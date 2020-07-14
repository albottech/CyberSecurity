# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
# copy application jar (with libraries inside)
COPY config-service.jar /config-service.jar
# specify default command
CMD ["/usr/bin/java", "-jar", "/config-service.jar"]
