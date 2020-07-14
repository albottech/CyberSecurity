# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
# copy application jar (with libraries inside)
COPY direct-stream-consumer-service.jar /stream-consumer-service.jar
# specify default command
CMD ["/usr/bin/java", "-jar", "/stream-consumer-service.jarr"]
