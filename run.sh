#!/usr/bin/env bash

gradle build
echo
java -jar ./build/libs/stocktracker-java-1.0-SNAPSHOT.jar
