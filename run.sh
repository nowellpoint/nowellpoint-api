mvn package -DskipTests=true
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/nowellpoint-api-jvm .
docker run --env AWS_REGION=$AWS_REGION --env AWS_ACCESS_KEY=$AWS_ACCESS_KEY --env AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY --env AWS_SECRET_NAME=$AWS_SECRET_NAME -i --rm -p 8080:8080 quarkus/nowellpoint-api-jvm
