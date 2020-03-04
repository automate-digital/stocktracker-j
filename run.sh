#!/usr/bin/env bash

./gradlew build
echo
java -jar ./build/libs/stocktracker-java-1.0-SNAPSHOT.jar
