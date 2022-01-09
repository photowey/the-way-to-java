#!/usr/bin/env bash
# variable01.sh

ip=192.168.0.11

# -----------------------------------------------------------------------------------
for i in {1..5}
do
    echo "$i times ping $ip"
    ping -c1 $ip &>/dev/null
    if [ $? -eq 0 ]; then
      echo "$ip is UP..."
    else
      echo "$ip is DOWN..."
    fi
done

# -----------------------------------------------------------------------------------
times=1
while [ $times -le 5 ]
do
    ping -c1 $ip &>/dev/null
    if [ $? -eq 0 ]; then
      echo "$ip is UP..."
    else
      echo "$ip is DOWN..."
    fi
    let times++
done