#!/usr/bin/env bash -xe

cd "$(dirname "$0")"

yarn
./test.sh
yarn run build
