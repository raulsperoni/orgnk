#!/usr/bin/env bash
docker build -t my-maven .
docker run -it -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven my-maven mvn package