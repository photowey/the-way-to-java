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
package com.photowey.translator.extension;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.JBColor;
import com.photowey.translator.App;
import com.photowey.translator.constant.TranslatorConstants;
import com.photowey.translator.home.Home;
import com.photowey.translator.property.TranslatorProperties;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * {@code TranslatorSettingsConfigure}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class TranslatorSettingsConfigure implements Configurable {

    private final JComponent component;
    private final JTextField appId;
    private final JTextField appSecret;
    private final static String appIdHint = "Input translator app appId";
    private final static String appSecretHint = "Input translator app appSecret";

    public TranslatorSettingsConfigure() {
        Home.check();

        this.component = new JPanel();
        this.component.setSize(200, 20);
        this.component.setLayout(new GridLayout(15, 1));

        this.appId = new JTextField();
        this.appSecret = new JTextField();

        TranslatorProperties translatorProperties = App.getConfigure().getTranslatorProperties();
        String appIdHintx = StringUtils.isNotBlank(translatorProperties.getAppId()) ? translatorProperties.getAppId() : appIdHint;
        String appSecretHintx = StringUtils.isNotBlank(translatorProperties.getAppSecret()) ? translatorProperties.getAppSecret() : appSecretHint;

        this.appId.setText(appIdHintx);
        this.appId.setForeground(JBColor.GRAY);
        this.appId.addFocusListener(new TextFieldListener(this.appId, appIdHintx));

        this.appSecret.setText(appSecretHintx);
        this.appSecret.setForeground(JBColor.GRAY);
        this.appSecret.addFocusListener(new TextFieldListener(this.appSecret, appSecretHintx));

        this.component.add(this.appId);
        this.component.add(this.appSecret);
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Translator";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return component;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        TranslatorProperties translatorProperties = App.getConfigure().getTranslatorProperties();
        translatorProperties.setAppId(appId.getText());
        translatorProperties.setAppSecret(appSecret.getText());

        TranslatorSettings translatorSettings = TranslatorSettings.getInstance();
        translatorSettings.setAppId(appId.getText());
        translatorSettings.setAppSecret(appSecret.getText());

        Home.init(translatorProperties.getAppId(), translatorProperties.getAppSecret());
    }

    static class TextFieldListener implements FocusListener {

        private final String defaultHint;
        private final JTextField textField;

        public TextFieldListener(JTextField textField, String defaultHint) {
            this.defaultHint = defaultHint;
            this.textField = textField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(defaultHint)) {
                textField.setText(TranslatorConstants.STRING_EMPTY);
                textField.setForeground(JBColor.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().equals(TranslatorConstants.STRING_EMPTY)) {
                textField.setText(defaultHint);
                textField.setForeground(JBColor.GRAY);
            }
        }
    }
}
