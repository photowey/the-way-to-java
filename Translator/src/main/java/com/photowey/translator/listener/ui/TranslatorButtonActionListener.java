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