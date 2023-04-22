docker stop roma-app
docker rm roma-app
docker rmi roma-image
docker build -t roma-image .
# -d image name
#docker run --name roma-app -p 8082:8080 -d roma-image
docker run --name roma-app -p 8082:8080 -v myvol:/data -d roma-image
docker exec -t -i roma-app ls
docker exec -t -i roma-app ./mvnv --version
docker logs roma-app
# -t container name
docker exec -t -i roma-app /bin/sh

# docker.io - public registry name
#docker tag roma-image:latest docker.io/epamromakotov/app:v1
#docker push docker.io/epamromakotov/app:v1
#docker pull docker.io/epamromakotov/app:v1
