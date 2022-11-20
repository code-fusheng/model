# !/bin/bash

PID = `ps aux | grep 9999 | grep java`
if [! "$PID" = ""]; then
  echo "PID not null"
  kill -9 $PID
  exit 1
fi