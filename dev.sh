#!/usr/bin/env bash

./gradlew clean build -x test
java -jar build/libs/*-fat.jar -conf=src/main/conf/dev.json
