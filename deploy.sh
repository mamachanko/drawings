#!/usr/bin/env bash -xe

./web-client/build.sh
./api/build.sh

cf push
