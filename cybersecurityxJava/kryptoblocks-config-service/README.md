

Do maven clean - install - all the test case will be executed and the final jar file will be created


#DOCKER build and run

1. 	MOVE 	DockerFile and config-service.jar to yor docker host build directory
2.	MAKE 	docker build -t "smartride-msa:config-service" .
3.	CHECK 	docker history smartride-msa:config-service
4.	RUN 	docker run -p 9000:9000 smartride-msa:config-service

#DOCKER compose and run
1. MOVE 	DockerFile, docker-compose.yml and config-service.jar to your docker host build directory 
2. RUN 		from the build directory execute docker-compose up
