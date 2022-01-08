#!/usr/bin/env bash

# 1.前后台作业控制
# &
# nohup
# ^C    结束前台进程
# ^Z
# bg %1
# fg %1
# kill %3
# screen

# 2.输入输出重定向 0-1-2
# >
# >>
# 2>
# 2>>
# 2>&1
# &>
# cat << /etc/hosts
# cat << EOF
# cat >file1 <<EOF

# 3.管道 | tee
# ip addr | grep 'inet' | grep eth0
# ip addr | grep 'inet' | tee test | grep eth0    覆盖
# ip addr | grep 'inet' | tee -a test | grep eth0    -a 追加

# df | grep '/$'
# df | tee df.txt | grep '/$'

# 命令排序
# ;    不具备逻辑判断能力
# cd;eject
# && || 具备逻辑判断能力
# ./configure && make && make install
# mkdir /var/111/222/333 && echo "Ok..."
# mkdir -p /var/111/222/333 && echo "Ok..."
# ls /var/111/222/333/444 || mkdir -p /var/111/222/333/444

# ping -c 1 192.168.1.101 && echo "UP" || echo "DOWN"
# ping -c 1 192.168.1.101 &>/dev/null && echo "UP" || echo "DOWN"

# -----------------------------------------------------------------------------------
# command &                             后台执行
# command &>/dev/null             混合重定向(标准输出:1  错误输出:2)
# command1 && command2        命令排序,逻辑执行
# -----------------------------------------------------------------------------------