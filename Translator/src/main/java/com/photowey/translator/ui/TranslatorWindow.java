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
package com.photowey.translator.ui;

import com.intellij.openapi.project.ProjectManager;
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
    private JComboBox<String> comboBox1;
    private com.intellij.util.textCompletion.TextFieldWithCompletion originalTextArea;
    private JComboBox<String> comboBox2;
    private JTextArea translateTextArea;
    private JButton translateButton;

    public TranslatorWindow() {
        comboBox1.addItem("英文");
        comboBox1.addItem("中文");
        comboBox2.addItem("中文");
        comboBox2.addItem("英文");

        String[] header = {"原文", "译文"};
        DefaultTableModel tableModel = new DefaultTableModel(null, header);
        noteTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        noteTable.setModel(tableModel);

        // 添加监听
        translateButton.addActionListener(new TranslatorButtonActionListener(this));
    }

    private void createUIComponents() {
        this.originalTextArea = new TextFieldWithCompletion(ProjectManager.getInstance().getDefaultProject(),
                new TranslatorTextProvider(), "", true, true, true, true);
    }

    // ----------------------------------------------------------------

    public JTabbedPane getTabbedPanel() {
        return tabbedPanel;
    }

    public void setTabbedPanel(JTabbedPane tabbedPanel) {
        this.tabbedPanel = tabbedPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getTranslatorPanel() {
        return translatorPanel;
    }

    public void setTranslatorPanel(JPanel translatorPanel) {
        this.translatorPanel = translatorPanel;
    }

    public JPanel getNotePanel() {
        return notePanel;
    }

    public void setNotePanel(JPanel notePanel) {
        this.notePanel = notePanel;
    }

    public JTable getNoteTable() {
        return noteTable;
    }

    public void setNoteTable(JTable noteTable) {
        this.noteTable = noteTable;
    }

    public JComboBox<String> getComboBox1() {
        return comboBox1;
    }

    public void setComboBox1(JComboBox<String> comboBox1) {
        this.comboBox1 = comboBox1;
    }

    public TextFieldWithCompletion getOriginalTextArea() {
        return originalTextArea;
    }

    public void setOriginalTextArea(TextFieldWithCompletion originalTextArea) {
        this.originalTextArea = originalTextArea;
    }

    public JComboBox<String> getComboBox2() {
        return comboBox2;
    }

    public void setComboBox2(JComboBox<String> comboBox2) {
        this.comboBox2 = comboBox2;
    }

    public JTextArea getTranslateTextArea() {
        return translateTextArea;
    }

    public void setTranslateTextArea(JTextArea translateTextArea) {
        this.translateTextArea = translateTextArea;
    }

    public JButton getTranslateButton() {
        return translateButton;
    }

    public void setTranslateButton(JButton translateButton) {
        this.translateButton = translateButton;
    }
}
