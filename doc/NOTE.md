docker login -u admin -p Harbor12345 42.192.222.62:9191
docker tag model:latest 42.192.222.62:9191/model/model:latest
docker push 42.192.222.62:9191/model/model:latest
