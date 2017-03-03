#!/usr/bin/env bash -xe

yarn
yarn run build
echo 'pushstate: enabled' > dist/Staticfile
