#!/bin/bash

echo pwd
cd /Users/fusheng/WorkSpace/CompanyWork/work-fusheng/model-pro/model/.bin

rsync -P "-e ssh -p 22221" -avz --progress . root@42.192.222.62:/root/Document/k8s/app/model/
