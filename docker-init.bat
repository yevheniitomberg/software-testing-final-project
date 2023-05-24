docker pull mysql:5.7
docker network rm network-name
docker network create network-name
docker run --name swedbank-database --network network-name -e MYSQL_ROOT_PASSWORD=yevhenii_tomberg -e MYSQL_DATABASE=swedbankdb -e MYSQL_USER=as -e MYSQL_PASSWORD=yevhenii_tomberg -d mysql
call ./gradlew build
SLEEP 10
docker build -t image-name .
docker run --network network-name --name app-container -p 8081:8081 -d image-name