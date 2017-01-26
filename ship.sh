#!/usr/bin/env bash -xe

git pull --rebase

./gradlew clean build

git push
