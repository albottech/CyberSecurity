#Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine

#Add service jar as app jar
ADD config-service.jar app.jar

RUN mkdir -p /log/config-service
RUN echo "starting config service" > /log/config-service/startingmsg

#Specify volume for log data
VOLUME /log


#Specify the entry point
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]
