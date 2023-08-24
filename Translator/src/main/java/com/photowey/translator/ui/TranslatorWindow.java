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
package com.photowey.translator.ui;

import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.GotItTooltip;
import com.intellij.util.textCompletion.TextFieldWithCompletion;
import com.photowey.translator.listener.ui.TranslatorButtonActionListener;
import com.photowey.translator.provider.TranslatorTextProvider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * {@code TranslatorWindow}
 *
 * @author photowey
 * @date 2022/11/14
 * @since 1.0.0
 */
public class TranslatorWindow {

    private JTabbedPane tabbedPanel;
    private JPanel mainPanel;
    private JPanel translatorPanel;
    private JPanel notePanel;
    private JTable noteTable;
    private JComboBox<String> en;
    private com.intellij.util.textCompletion.TextFieldWithCompletion originalTextArea;
    private JComboBox<String> zh;
    private JTextArea translateTextArea;
    private JButton translateButton;

    public TranslatorWindow() {
        this.en.addItem("英文");
        this.en.addItem("中文");
        this.zh.addItem("中文");
        this.zh.addItem("英文");

        String[] header = {"原文", "译文"};
        DefaultTableModel tableModel = new DefaultTableModel(null, header);
        this.noteTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        this.noteTable.setModel(tableModel);

        // 添加监听
        this.translateButton.addActionListener(new TranslatorButtonActionListener(this));

        this.gotItNotify();
    }

    private void gotItNotify() {
        tabbedPanel.addChangeListener(e -> {
            JTabbedPane tab = (JTabbedPane) e.getSource();
            if (tab.getSelectedIndex() == 0) {
                return;
            }
            new GotItTooltip("got.it.id", "翻译插件", ProjectManager.getInstance().getDefaultProject()).
                    withShowCount(100).
                    withHeader("输入文本, 点击翻译按钮即可完成翻译").
                    show(translateButton, GotItTooltip.BOTTOM_MIDDLE);
        });
    }

    private void createUIComponents() {
        this.originalTextArea = new TextFieldWithCompletion(ProjectManager.getInstance().getDefaultProject(),
                new TranslatorTextProvider(), "", true, true, true, true);
    }

    // ----------------------------------------------------------------

    public JTabbedPane getTabbedPanel() {
        return tabbedPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getTranslatorPanel() {
        return translatorPanel;
    }

    public JPanel getNotePanel() {
        return notePanel;
    }

    public JTable getNoteTable() {
        return noteTable;
    }

    public JComboBox<String> getEn() {
        return en;
    }

    public TextFieldWithCompletion getOriginalTextArea() {
        return originalTextArea;
    }

    public JComboBox<String> getZh() {
        return zh;
    }

    public JTextArea getTranslateTextArea() {
        return translateTextArea;
    }

    public JButton getTranslateButton() {
        return translateButton;
    }
}
