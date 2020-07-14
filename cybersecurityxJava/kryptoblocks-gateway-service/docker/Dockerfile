#Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine

#Add service jar as app jar
ADD gateway-service.jar app.jar

RUN mkdir -p /log/gateway-service
RUN echo "starting gateway service" > /log/gateway-service/startingmsg

#Specify volume for log data
VOLUME /log

#Expose the HTTP port
EXPOSE 8100


#Specify the entry point
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]
