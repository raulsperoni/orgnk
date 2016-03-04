#!/bin/bash
export COMPOSE_API_VERSION=auto
cp ./production/persistenceDevelopment.xml ./nitro-ejb/src/main/resources/META-INF/persistence.xml
sh mavenDeploy.sh
docker-compose up -d wildfy

