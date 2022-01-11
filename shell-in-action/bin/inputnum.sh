#!/usr/bin/env bash
# inputnum.sh

# 判断用户输入的是不是数字

read -p "请输入一个数字: " num

if [[ ! "$num" =~ ^[0-9]+$ ]]; then
  echo "您输入的不是一个数字,程序退出"
  exit
fi
