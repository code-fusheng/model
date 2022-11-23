#!/bin/bash

docker login -u admin -p Harbor12345 42.192.222.62:9191

docker tag model:latest 42.192.222.62:9191/model/model:latest
docker push 42.192.222.62:9191/model/model:latest

cd /root/Document/k8s/app/model

kubectl delete -f yaml.d/model-prod.yaml
kubectl apply -f yaml.d/model-prod.yaml
