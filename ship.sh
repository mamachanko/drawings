#!/usr/bin/env bash -xe

git pull --rebase

./api/build.sh
./web-client/build.sh

git push
