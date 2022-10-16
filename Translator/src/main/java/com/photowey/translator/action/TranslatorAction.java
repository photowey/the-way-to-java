package com.photowey.translator.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.photowey.translator.App;
import com.photowey.translator.extension.TranslatorCache;
import com.photowey.translator.handler.TranslateHandler;
import com.photowey.translator.home.Home;
import com.photowey.translator.home.HomeData;
import com.photowey.translator.property.TranslatorProperties;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
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

        if (StringUtils.isBlank(appId) || StringUtils.isBlank(appSecret)) {
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
        String query = Objects.requireNonNull(editor).getSelectionModel().getSelectedText();
        TranslatorCache translatorCache = TranslatorCache.getInstance(Objects.requireNonNull(event.getProject()));

        Map<String, String> translateCache = translatorCache.getTranslateCache();

        String cacheKey = this.populateCacheKey(query);

        String translateResult = query;
        if (translateCache.containsKey(query)) {
            translateResult = translateCache.get(cacheKey);
        } else {
            if (StringUtils.isBlank(query)) {
                Notification notification = new Notification(
                        "Translator",
                        "Translate Warnning",
                        "Please select translate query",
                        NotificationType.WARNING
                );
                Notifications.Bus.notify(notification, event.getProject());
                return;
            }

            HomeData homeData = Home.localHomeData();
            HomeData.Cache cache = homeData.getCache();

            translateResult = cache.get(cacheKey);
            if (StringUtils.isBlank(translateResult)) {
                TranslateHandler translateHandler = App.getConfigure().getTranslateHandler();
                translateResult = translateHandler.handleTranslate(query, "auto", "zh");
                translateCache.put(cacheKey, translateResult);
                cache.put(cacheKey, translateResult);
            }
        }

        Notification notification = new Notification(
                "Translator",
                "Translate Result",
                translateResult,
                NotificationType.INFORMATION
        );
        Notifications.Bus.notify(notification, event.getProject());
    }

    private String populateCacheKey(String query) {
        String key = query + "." + "zh";

        return key;
    }
}
