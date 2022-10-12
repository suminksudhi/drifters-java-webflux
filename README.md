### Build Project
```shell
mvn clean package
```
#### Build Image
```shell
mvn spring-boot:build-image
```

### Bring up server
```shell
docker compose up
```

### Bring down system
```shell
docker compose down
```

### Scale system
```shell
docker compose --file docker-compose-scale.yml up -d --build --scale message-server=3 product-server=2
```

### Remove all docker
```shell
docker rm -f $(docker ps -aq)
```

docker run -it -v $(pwd):/tmp mongo:5.0 mongoimport --drop --collection=COLLECTION "mongodb+srv://user:password@clusterURL/database" /tmp/COLLECTION.json