#!/usr/bin/env bash
# inputnum02.sh

# 判断用户输入的是不是数字

read -p "请输入一个数字: " num

while :
do
  if [[ "$num" =~ ^[0-9]+$ ]]; then
    break
  else
    read -p "$num 不是数字, 请输入一个数字: " num
  fi

done

echo "您输入的数字是: $num"
