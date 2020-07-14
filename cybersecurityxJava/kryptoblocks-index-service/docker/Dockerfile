# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
# copy application jar (with libraries inside)
COPY index-service.jar /index-service.jar
# specify default command
CMD ["/usr/bin/java", "-jar", "/index-service.jar"]