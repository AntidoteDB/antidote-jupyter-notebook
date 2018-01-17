#!/bin/bash

docker-compose stop
docker-compose rm -f antidote1
docker-compose rm -f antidote2
docker-compose run link
