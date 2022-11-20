mvn clean package docker:build -Dmaven.test.skip=true -Pprod

docker login -u admin -p Harbor12345 42.192.222.62:9191
docker tag model:latest 42.192.222.62:9191/model/model:latest
docker push 42.192.222.62:9191/model/model:latest


#!/bin/bash

SERVER_NAME=model

# 源 jar 路径，target 目录下的 jar 包
JAR_NAME=model-2.0.0

# target 打包生成 jar 包目录
# 以具体的打包位置为准，可以先构建一次项目，通过日志查看打包的目录
JAR_PATH=/var/jenkins_home/workspace/model/target

# 打包完成之后，把 jar 包移动到运行 jar 包的目录
JAR_WORK_PATH=/var/jenkins_home/workspace/model/target

echo "查询进程id-->$SERVER_NAME"
PID=`ps -ef | grep "$SERVER_NAME" | awk '{print $2}'`
echo"得到进程ID: $PID"
echo"结束进程"
for id in $PID
do
kill -9 $id
echo "killed $id"
done
echo"结束进程完成"
#复制jar包到执行目录_
echo" 复制jar包到执行目录:cp $JAR_PATH/$JAR_NAME.jar $JAR_WORK_PATH"
cp $JAR_PATH/$JAR_NAME.jar $JAR_WORK_PATH
echo"复 制jar包完成"
cd $JAR_WORK_PATH
#修改文件权限
chmod 755 $JAR_NAME.jar
#前台启动
#java -jar $JAR_NAME.jar
#后台启动
BUILD_ID=dontKillMe nohup java -jar $JAR_NAME.jar &


### 废弃内容
#以下为fastdfs的配置
fdfs:
  so-timeout: 2500
  connect-timeout: 600
  thumb-image:
    width: 100
    height: 100
  tracker-list:
    - 42.192.222.62:22122
upload:
  base-url: http://175.24.45.179:8888/
  allow-types:
    - image/jpeg
    - image/png
    - image/bmp
    - image/gif
    - audio/mpeg

  # 以下为kafka的配置
  #  kafka:
  #    producer:
  #      bootstrap-servers: 47.93.19.102:9092
