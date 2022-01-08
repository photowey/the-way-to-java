#!/usr/bin/env bash

IP=192.168.1.6
ping -c 1 $IP &>/dev/null
if [ $? -eq 0 ]; then
  echo "$IP is UP"
else
  echo "$IP is DOWN"
fi