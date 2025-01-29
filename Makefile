SHELL := /bin/bash

# 默认使用 mvn, 如果 MVND_HOME 环境变量存在, 则使用 mvnd
MVN ?= $(if $(MVND_HOME),mvnd,mvn)

.PHONY: clean compile test deploy package tree

# 设置默认目标
.DEFAULT_GOAL := help

# 打印当前目录
dir:
	@echo "Current directory: $(CURDIR)"

# 清理项目
clean: dir
	@echo "Cleaning the project..."
	$(MVN) clean

# 编译项目
compile: clean
	@echo "Using $(MVN) to compile the project..."
	$(MVN) compile

# 运行测试
test: clean
	@echo "Using $(MVN) to test the project..."
	$(MVN) test

# 部署项目
deploy: clean
	@echo "Using $(MVN) to deploy the project..."
	$(MVN) -DskipTests=true source:jar deploy

# 打包项目
package: clean
	@echo "Using $(MVN) to package the project..."
	$(MVN) -DskipTests=true package

# 查看依赖树
tree:
	@echo "Using $(MVN) to show dependency tree..."
	$(MVN) dependency:tree -Dincludes=$(filter-out $@,$(MAKECMDGOALS))

# 帮助信息
help:
	@echo "Available targets:"
	@echo "  clean     - Clean the project"
	@echo "  compile   - Compile the project"
	@echo "  test      - Run tests"
	@echo "  deploy    - Deploy the project"
	@echo "  package   - Package the project"
	@echo "  tree      - Show dependency tree (e.g., make tree group:artifact)"
	@echo "  help      - Show this help message"

# 处理未知目标
%:
	@echo "Unknown target: $@"
	@echo "Use 'make help' to see available targets."
