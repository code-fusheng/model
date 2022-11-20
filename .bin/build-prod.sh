#!/bin/sh
# 开始构建 Docker 微服务镜像

echo pwd
cd /Users/zhanghao/IdeaProjects/fusheng-work/git/model/model/.bin

cd ..
mvn clean install package docker:build -P prod -D skipTests
