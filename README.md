# Spring Web flux
Sample project in spring web flux, mongo reactive

### Build Project 
```shell
mvn clean -DskipTests package
```
#### Build Image
```shell
docker compose --env-file ./config/.env.dev build 
```
#### Bring up server
```shell
docker compose --env-file ./config/.env.dev up 
```
### Api test
After services are running
```shell
mvn test
```
#### Bring down system
```shell
docker compose --env-file ./config/.env.dev down
```
#### Scale system
```shell
docker compose --env-file ./config/.env.dev  --file docker-compose-scale.yml up -d --build --scale review-service=3 --scale=product-service=2
```
#### Remove all docker
```shell
docker rm -f $(docker ps -aq)
```

## Project specifics
### Sample adidas products for live api
#### Sample used for review seed
['EG4959', 'EE6999', 'GY6701', 'EG4958', 'FX5499']

#### Additional sample products from site
['FX5499', 'BB5478', 'GV8745', 'DB3021', 'GX4274', 'GW7954', 'GZ9256', 'EE6461', 'G27706', 'GX4602', 'H06395', 'GV7122', 'GW8591', 'GZ5173', 'GW9152', 'FZ0961', 'FX8707', 'HP5358', 'HQ4276', 'BB5476', 'GW9284', 'FW7439', 'EG4957', 'FX5090', 'B43814', 'H67366', 'GY4146', 'H03075', 'BB5498', 'GX9524', 'GZ8185', 'GY4424', 'GW2055', 'FX6029', 'BD7633', 'HP7902', 'GX6766', 'B23707', 'FX5496', 'GV8763', 'GZ2812', 'GX2206', 'GW8204', 'HQ9873']

####  Sample obtained from
```shell
// product
https://www.adidas.co.uk/api/products/EG4958
// review
https://www.adidas.co.uk/api/models/IUU93/reviews?bazaarVoiceLocale=en_GB&includeLocales=en%2A&limit=7&offset=0&reviewIds=&sort=relevant
```
### Seed Review Data
```shell
docker compose run db mongoimport --drop --collection=reviews "mongodb://db/drifter-db" /seed/reviews.json --jsonArray
```

### Api aggregation
aggregate api results and show in one response nodes averageReviewScore/numberOfReviews

![alt text](https://raw.githubusercontent.com/suminksudhi/drifters-java-webflux/main/images/product-service.png)