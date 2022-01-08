#!/usr/bin/env bash

# 隐式-赋值 - 读取键盘输入
# read IP
read -p "Please input a IP: " IP

ping -c 1 $IP &>/dev/null
if [ $? -eq 0 ]; then
  echo "$IP is UP"
else
  echo "$IP is DOWN"
fi