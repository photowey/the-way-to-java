#!/usr/bin/env bash

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