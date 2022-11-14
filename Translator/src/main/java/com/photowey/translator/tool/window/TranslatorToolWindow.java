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
package com.photowey.translator.tool.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * {@code TranslatorToolWindow}
 *
 * @author photowey
 * @date 2022/11/14
 * @since 1.0.0
 */
public class TranslatorToolWindow implements ToolWindowFactory {

    private static JTable table;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        TranslatorNote note = new TranslatorNote();
        table = note.getTable();
        Content content = contentFactory.createContent(note.getNotePanel(), "", false);
        toolWindow.getContentManager().addContent(content);
    }

    static class TranslatorNote {
        private final JScrollPane notePanel;
        private final JTable table;

        public TranslatorNote() {
            String[] header = {"原文", "译文"};
            DefaultTableModel tableModel = new DefaultTableModel(null, header);
            this.table = new JTable();
            this.table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            this.table.setModel(tableModel);
            this.notePanel = new JBScrollPane(table);
            this.notePanel.setSize(200, 800);
        }

        public JScrollPane getNotePanel() {
            return notePanel;
        }

        public JTable getTable() {
            return table;
        }
    }

    public static void addNote(String from, String to) {
        if (table == null) {
            return;
        }
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        Vector<Vector> dataVector = tableModel.getDataVector();
        for (Vector vector : dataVector) {
            if (vector.get(0).equals(from)) {
                return;
            }
        }

        tableModel.addRow(new Object[]{from, to});
    }

}