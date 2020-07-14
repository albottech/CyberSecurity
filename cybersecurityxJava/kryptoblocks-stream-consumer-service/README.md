#DOCKER build and run

1. 	MOVE 	DockerFile and direct-stream-consumer-servic.jar to yor docker host build directory
2.	MAKE 	docker build -t "smartride-msa:direct-stream-consumer-service" .
3.	CHECK 	docker history smartride-msa:direct-stream-consumer-service
4.	RUN 	docker run -p 7050:7050 metlife-msa:direct-stream-consumer-service

#DOCKER compose and run
1. MOVE 	DockerFile, docker-compose.yml and direct-stream-consumer-servic.jar to your docker host build directory 
2. RUN 		from the build directory execute docker-compose up
