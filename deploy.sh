#!/usr/bin/env bash -xe

cd "$(dirname "$0")"

./web-client/build.sh
./api/build.sh

cf push
