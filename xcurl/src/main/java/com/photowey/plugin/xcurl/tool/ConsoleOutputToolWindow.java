/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.plugin.xcurl.tool;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code ConsoleOutputToolWindow}
 *
 * @author photowey
 * @date 2022/11/16
 * @since 1.0.0
 */
public class ConsoleOutputToolWindow implements ToolWindowFactory {

    private static final Map<Project, ConsoleView> CONSOLE_VIEWS = new HashMap<>();
    public static ToolWindow toolWindow;
    public static final String HTTPZ_CONSOLE_ID = "Xcurl";
    public static final String XCURL_WINDOW = "👇Output👇";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (CONSOLE_VIEWS.get(project) == null) {
            createToolWindow(project, toolWindow);
        }

        ConsoleOutputToolWindow.toolWindow = toolWindow;
    }

    public static ConsoleView getConsoleView(Project project) {
        if (CONSOLE_VIEWS.get(project) == null) {
            ToolWindow toolWindow = getToolWindow(project);
            createToolWindow(project, toolWindow);
        }

        return CONSOLE_VIEWS.get(project);
    }

    public static void show(Project project, String content) {
        show(project, "", content);
    }

    public static void show(Project project, String url, String content) {
        show(project, url, content, ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public static void show(Project project, String url, String content, ConsoleViewContentType contentType) {
        // @formatter:off
        if (StringUtils.isEmpty(content)) {
            return;
        }
        ConsoleView cv = getConsoleView(project);

        StringBuilder output = new StringBuilder();
        output.append("----------------------------------------------------------------");
        output.append("\n");
        if (StringUtils.isNotBlank(url)) {
            output.append(url);
            output.append("\n");
            output.append("-------");
            output.append("\n");
        }
        output.append("Output:");
        output.append("\n");
        output.append("-------");
        output.append("\n");
        output.append(content);
        output.append("\n");

        // activate: console
        toolWindow.activate(()->{},false);
        cv.print(output.toString(), contentType);
        // @formatter:on
    }

    private static void createToolWindow(Project project, ToolWindow toolWindow) {
        // @formatter:off
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        CONSOLE_VIEWS.put(project, consoleView);
        Content content = toolWindow
                .getContentManager()
                .getFactory()
                .createContent(consoleView.getComponent(), XCURL_WINDOW, false);
        content.setCloseable(true);

        JComponent component = content.getComponent();
        component.setVisible(true);

        ContentManager contentManager = toolWindow.getContentManager();
        contentManager.addContent(content);
        contentManager.setSelectedContent(content);
        // @formatter:on
    }

    public static ToolWindow getToolWindow(@NotNull Project project) {
        return ToolWindowManager.getInstance(project).getToolWindow(HTTPZ_CONSOLE_ID);
    }
}
