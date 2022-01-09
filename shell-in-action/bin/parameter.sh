#!/usr/bin/env bash
# parameter.sh

# 如果用户没有输入参数-则提示
if [ $# -eq 0 ]; then
  echo "Usage: `basename $0` file"
  exit
fi

# 如果用户输入参数不是一个文件-则提示
if [ ! -f $1 ]; then
  echo "the $1 not a file"
  exit
fi

# for
for ip in `cat $1`
do
  ping -c 1 $ip &>/dev/null
  if [ $? -eq 0 ]; then
    echo "$ip is UP."
  else
    echo "$ip is DOWN."
  fi
done