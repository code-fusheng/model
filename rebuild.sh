# !/bin/bash
# 如果 MAC 提示 zsh: permission denied: ./rebuild.sh
# 请执行 chmod u+x rebuild.sh
rm -rf target/*
mvn -Dmaven.test.skip=true clean package
rm -rf target/*.gz

