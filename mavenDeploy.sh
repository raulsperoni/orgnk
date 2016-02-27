#!/usr/bin/env bash
docker run -it -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven my-maven mvn package