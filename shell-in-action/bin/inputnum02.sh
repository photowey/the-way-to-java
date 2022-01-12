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

# inputnum02.sh

# 判断用户输入的是不是数字

read -p "请输入一个数字: " num

while :; do
  if [[ "$num" =~ ^[0-9]+$ ]]; then
    break
  else
    read -p "$num 不是数字, 请输入一个数字: " num
  fi

done

echo "您输入的数字是: $num"
