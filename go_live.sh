#!/bin/bash

cp ./production/persistenceProduction.xml ./nitro-ejb/src/main/resources/META-INF/persistence.xml
sh mavenDeploy.sh
