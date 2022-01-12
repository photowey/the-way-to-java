#!/usr/bin/env bash
#
# Copyright © 2021 photowey (photowey@gmail.com)
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

# parameter.sh

# 如果用户没有输入参数-则提示
if [ $# -eq 0 ]; then
  echo "Usage: $(basename $0) file"
  exit
fi

# 如果用户输入参数不是一个文件-则提示
if [ ! -f $1 ]; then
  echo "the $1 not a file"
  exit
fi

# for
for ip in $(cat $1); do
  ping -c 1 $ip &>/dev/null
  if [ $? -eq 0 ]; then
    echo "$ip is UP."
  else
    echo "$ip is DOWN."
  fi
done
