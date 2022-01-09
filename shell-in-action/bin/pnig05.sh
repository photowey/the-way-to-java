#!/usr/bin/env bash
# ping05.sh

# $1 $2 ... $n    位置变量

ping -c 1 "$1" &>/dev/null
if [ $? -eq 0 ]; then
  echo "$1 is UP"
else
  echo "$1 is DOWN"
fi