#!/bin/bash

echo pwd
cd /Users/zhanghao/IdeaProjects/fusheng-work/git/model/model/.bin

rsync -P "-e ssh -p 22221" -avz --progress . root@42.192.222.62:/root/Document/k8s/app/model/
