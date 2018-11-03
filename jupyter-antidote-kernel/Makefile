VERSION=$(shell cat VERSION)
IMAGE=antidotedb/notebook

all: jar docker

jar:
	mvn package -DskipTests

docker: jar
	docker build -t $(IMAGE) src/docker/antidotenb

runnotebook:
	cd src/docker && docker-compose up

push:
	cd src/docker/antidotenb && docker push $(IMAGE)

clean:
	mvn clean
	if [ -e src/docker/antidotenb/*.jar ] ; then rm src/docker/antidotenb/*.jar ; fi



