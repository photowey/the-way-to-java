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

IP=192.168.1.6
ping -c 1 $IP &>/dev/null
if [ $? -eq 0 ]; then
  echo "$IP is UP"
else
  echo "$IP is DOWN"
fi
