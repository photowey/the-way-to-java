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

# variable02.sh

# 变量内容的删除和替换

url=www.opencharity.org.cn

echo $url

# ------------------------------------------------------------- 内容的删除
echo "---------------------------delete---------------------------"
# 获取变量的长度
echo ${#url}

# 从前往后,最短匹配
echo ${url#*.}
# opencharity.org.cn

# 从前往后,最长匹配 - 贪婪模式
echo ${url##*.}
# cn

# 从后往前,最短匹配
echo ${url%.*}
# www.opencharity.org

# 从后往前,最长匹配 - 贪婪模式
echo ${url%%.*}
# www

# ------------------------------------------------------------- 索引及切片
echo "---------------------------index---------------------------"
echo ${url:0:5}
# www.o

echo ${url:5:5}
# pench

echo ${url:5}
# pencharity.org.cn

# ------------------------------------------------------------- 内容的替换
echo "---------------------------replace---------------------------"
echo ${url/opencharity/baidu}
# www.baidu.org.cn

echo ${url/n/N}
# www.opeNcharity.org.cn

# 贪婪模式
echo ${url//n/N}
# www.opeNcharity.org.cN

# ------------------------------------------------------------- 默认值
echo "---------------------------default-value---------------------------"
# ${变量名-新的变量值}
# - 变量没有被赋值: 会使用"新的变量值"替换
# - 变量被赋值(包括空值: var1=): 不会被替换

# ${变量名:-新的变量值}
# - 变量没有被赋值(包括空值): 会使用"新的变量值"替换
# - 变量有被赋值: 不会被替换

unset var1

echo "---------------------------var1-default1---------------------------"
echo ${var1-defaultValue1}
var1="hello shell"
echo "---------------------------var1-default2---------------------------"
echo ${var1-defaultValue2}

echo "---------------------------var2---------------------------"
unset var2
var2=
echo ${var2-defaultValue3}

unset var1
unset var2
