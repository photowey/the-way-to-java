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

# variable.sh

# 1.变量的赋值方式
# 1.1.显示赋值
# - 变量名=变量值
# 示例:
# - ip1=192.168.1.101
# - school="haha school"
# - today1=`date +%F`
# - today2=$(date +%)

# 1.2.read 从键盘 读入变量值
# 示例:
# - read 变量名
# - read -p "提示信息: " 变量名
# - read -t 5 -p "提示信息: " 变量名    -t: 等待时间
# - read -n 2 变量名                            -n 2: 2次的输入

# ⭐⭐
# 定义变量和引用变量时注意
# - ""    弱引用
# - ‘’    强引用 - 也就是说: 在单引号中没有变量引用这一说法

# 命令替换
# - `cmd` == $(cmd)
# - disk_free=`df -Ph | grep '/$' | awk '{print $4}'`
# - disk_free=$(df -Ph | grep '/$' | awk '{print $4}')

# -----------------------------------------------------------------------------------
# 变量运算
# -----------------------------------------------------------------------------------
# 1.整数运算
# - 1.1. 方法一: expr             + - \* / %
# -- expr 1 + 1
# -- expr $num1 + $num2

# - 1.2. 方法二: $(())            + - * / %
# -- echo $(($num1 + $num2))
# -- echo $((num1 + num2))
# -- echo $((5-3*2))
# -- echo $(((5-3)*2))
# -- echo $((2**3))
# -- sum=$((1+2)); echo $sum

# - 1.3. 方法三: $[]              + - * / %
# -- echo $[5+2]
# -- echo $[5**2]

# 1.4. 方法四: let
# -- let sum=2+3; echo $sum
#-- let i++; echo $i

# -----------------------------------------------------------------------------------
# 2.小数运算
# echo "2*4" | bc
# echo "2^4" | bc
# echo "scale=2;6/4" | bc
# awk 'BEGIN{print 1/2}'
# echo "print 5.0/2" | python
