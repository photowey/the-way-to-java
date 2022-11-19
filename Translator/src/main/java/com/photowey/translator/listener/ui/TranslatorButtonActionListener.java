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
package com.photowey.translator.listener.ui;

import com.photowey.translator.App;
import com.photowey.translator.handler.TranslateHandler;
import com.photowey.translator.provider.TranslatorTextProvider;
import com.photowey.translator.ui.TranslatorWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code TranslatorButtonActionListener}
 *
 * @author photowey
 * @date 2022/11/14
 * @since 1.0.0
 */
public class TranslatorButtonActionListener extends AbstractAction {

    private final TranslatorWindow window;
    private final Map<String, String> langMap;

    public TranslatorButtonActionListener(TranslatorWindow window) {
        this.window = window;
        this.langMap = new HashMap<>();
        this.langMap.put("中文", "zh");
        this.langMap.put("英文", "en");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query = window.getOriginalTextArea().getText();
        String fromLang = langMap.get((String) window.getEn().getSelectedItem());
        String toLang = langMap.get((String) window.getZh().getSelectedItem());

        TranslateHandler translateHandler = App.getConfigure().getTranslateHandler();
        String translateResult = translateHandler.handleTranslate(query, fromLang, toLang);

        window.getTranslateTextArea().setText(translateResult);

        TranslatorTextProvider.items.add(query);
        TranslatorTextProvider.items.add(translateResult);
    }
}