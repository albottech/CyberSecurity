#DOCKER build and run

1. 	MOVE 	DockerFile and direct-stream-producer-service.jar to yor docker host build directory
2.	MAKE 	docker build -t "smartride-msa:direct-stream-producer-service" .
3.	CHECK 	docker history smartride-msa:direct-stream-producer-service
4.	RUN 	docker run -p 7040:7040 smartride-msa:direct-stream-producer-servicee

#DOCKER compose and run
1. MOVE 	DockerFile, docker-compose.yml and direct-stream-provider-servic.jar to your docker host build directory 
2. RUN 		from the build directory execute docker-compose up
