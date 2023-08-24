/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.netty.telnet.in.action.netty.command;

import com.photowey.netty.telnet.in.action.constant.NamedConstants;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.io.Serializable;

/**
 * {@code NamedCommand}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NamedCommand extends AbstractCommandAdaptor implements Serializable {

    private static final long serialVersionUID = -6660076061145688817L;

    public NamedCommand(String command) {
        this.command = command;
    }

    @Override
    public String name() {
        return "named";
    }

    @Override
    public boolean validate() {
        String[] syntax = command.trim().split(NamedConstants.SPACE_SEPARATOR);
        return this.name().equals(syntax[0]);
    }

    @Override
    protected String doExecute(NamedCommandLine commandLine) throws Exception {
        // TODO 通过 策略来解析不同的命令行
        return this.process();
    }

    /**
     * 这儿模仿-进行了一些操作 - 返回了 表格式的结果
     */
    String process() {
        String[] students = {"小明", "李雷", "韩梅梅"};
        double[] scores = {90.1, 84.3, 99.7};
        double[] chinese = {90.1, 84.3, 99.7};
        double[] data = {90.1, 84.3, 99.7};
        double[] english = {90.1, 84.3, 99.7};
        double[] physical = {90.1, 84.3, 99.7};
        double[] chemistry = {90.1, 84.3, 99.7};
        Table table = Table.create("学生分数统计表-模仿某些操作返回列表").addColumns(
                StringColumn.create("姓名", students),
                DoubleColumn.create("综合", scores),
                DoubleColumn.create("语文", chinese),
                DoubleColumn.create("数据", data),
                DoubleColumn.create("英语", english),
                DoubleColumn.create("物理", physical),
                DoubleColumn.create("化学", chemistry)
        );
        String tableStr = table.print().replaceAll("\r\n", "\n");
        return tableStr;
    }
}
