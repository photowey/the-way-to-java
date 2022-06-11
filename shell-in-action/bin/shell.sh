#!/usr/bin/env bash
#
# Copyright © 2021 the original author or authors (photowey@gmail.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

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

# 4.命令排序
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

# 5.变量
# 5.1.变量的类型
# 5.1.1.自定义变量
# - 定义变量    变量名=变量值 - 变量名必须以字母和下划线开头,区分大小写
# - 引用变量    $变量名 || ${变量名}
# - 查看变量    echo $变量名 set(所有变量: 自定义变量和环境变量)
# - 取消变量    unset 变量名
# - 作用范围    仅在当前 shell 中生效

# 5.2.环境变量
# 5.2.1.自定义环境变量
# - 定义环境变量
# -- export xxx=yyy
# -- export zzz 将自定义变量转换为环境变量
# - 引用环境变量    $变量名 || ${变量名}
# - 查看环境变量    echo $变量名 || env -> env | grep JAVA_HOME
# - 取消用环境变量    unset 变量名
# - 变量作用范围    当前 shell 和 子 shell

# 5.3.位置变量
# - $1 $2 $3 $4 $5 $6 $7 $8 $9 ${10}

# 5.4.预定义变量
# - $0    脚本名
# - $*    所有的参数
# - $@    所有的参数
# - $#    参数的个数
# - $$    当前进程的PID
# - $!    上一个 - 后台 - 进程的PID
# - $?    上一个命令的返回值 0:成功

# ------------------------------------------------------------- 特殊符号
# () 子 shell 中执行
# (())    数值比较,运算    ((1<2))
# $()    命令替换 == ``
# $(())    整数运算    echo $((1+2))

# {}    集合    {1..5}
# ${}    变量-删除-替换...

# []    条件测试                                     [ -d /home -a -f /etc/files ]; echo $?
# [[]]    条件测试, 支持正则 =~              [[ -d /home && -f /etc/files ]]; echo $?
# $[]    整数运算                                  $[2**10]

# -----------------------------------------------------------------------------------
# 执行脚本
# ./shell.sh                             需要执行权限, 在子 shell 中执行
# bash shell.sh                       不需要执行权限, 在子 shell 中执行

# . shell.sh                             不需要执行权限, 在当前 shell 中执行
# source shell.sh                   不需要执行权限, 在当前 shell 中执行
# 通常修改系统配置文件中(如: /etc/profile) 的PATH等变量后, 使之在当前 shell 中 生效

# 调试脚本
# sh -n shell.sh                       仅调试语法错误
# sh -vx shell.sh                     以调试的方式执行, 查询整个执行过程
