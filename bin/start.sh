# !/bin/bash

PID = `ps aux | grep 9999 | grep java`
echo "PID:"$PID
if [! "$PID" = ""]; then
  echo "PID not null"
  kill -9 $PID
  exit 1
fi

cd target/
nohup java -jar model-1.0.2-SNAPSHOT.jar --server.port=9999

# nohup java -jar model-1.0.2-SNAPSHOT.jar --server.port=9999  > model.log>&1 &

tail -f logs/model-all.log

