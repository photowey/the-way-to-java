#!/usr/bin/env bash

# 变量 显式赋值
IP=192.168.1.6
if ping -c 1 $IP &>/dev/null; then
  echo "$IP is UP"
else
  echo "$IP is DOWN"
fi