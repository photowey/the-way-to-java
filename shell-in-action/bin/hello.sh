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

echo "hello bash"

rpm -qc bash

# 1.命令和文件自动补齐
rpm -qa | grep bash-com

# 2.命令
# 2.1. !number    复现某条历史命令
# - !1024
# 2.2. !string    找到最近xx开头的命令
# - !da
# 2.3. !$    上一个命令的最后一个参数
# 2.4. !!    上一条命令
# 2.5. ^R    搜索历史命令
