#!/usr/bin/env bash -e

./gradlew clean assemble

cf push -p build/libs/project-kelleybert-0.0.1-SNAPSHOT.jar

