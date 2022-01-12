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

# variable01.sh

ip=192.168.0.11

# -----------------------------------------------------------------------------------
for i in {1..5}; do
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
while [ $times -le 5 ]; do
  ping -c1 $ip &>/dev/null
  if [ $? -eq 0 ]; then
    echo "$ip is UP..."
  else
    echo "$ip is DOWN..."
  fi
  let times++
done
