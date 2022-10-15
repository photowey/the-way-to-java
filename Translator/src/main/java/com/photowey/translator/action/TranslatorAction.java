package com.photowey.translator.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.photowey.translator.App;
import com.photowey.translator.handler.TranslateHandler;
import com.photowey.translator.property.TranslatorProperties;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * {@code TranslatorAction}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class TranslatorAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        TranslatorProperties translatorProperties = App.getConfigure().getTranslatorProperties();

        String appId = translatorProperties.getAppId();
        String appSecret = translatorProperties.getAppSecret();

        if (appId == null || appSecret == null) {
            Notification notification = new Notification(
                    "Translator",
                    "Translate Error",
                    "Please set appId,appSecret first",
                    NotificationType.ERROR
            );
            Notifications.Bus.notify(notification, event.getProject());
            return;
        }
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        String text = Objects.requireNonNull(editor).getSelectionModel().getSelectedText();
        if(StringUtils.isBlank(text)) {
            Notification notification = new Notification(
                    "Translator",
                    "Translate Warnning",
                    "Please select translate text",
                    NotificationType.ERROR
            );
            Notifications.Bus.notify(notification, event.getProject());
            return;
        }
        TranslateHandler translateHandler = App.getConfigure().getTranslateHandler();
        String transResult = translateHandler.handleTranslate(text, "auto", "zh");
        Notifications.Bus.notify(new Notification("Translator", "Translate Result", transResult, NotificationType.INFORMATION), event.getProject());
    }
}
