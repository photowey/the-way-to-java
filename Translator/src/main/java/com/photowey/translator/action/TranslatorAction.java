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
package com.photowey.translator.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.NlsContexts;
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
            this.busNotifyError("Translator", "Translate Error", "Please set appId,appSecret first", event);
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
                this.busNotifyWarning("Translator", "Translate Warnning", "Please select translate query", event);
                return;
            }

            HomeData homeData = Home.localHomeData();
            HomeData.Cache cache = Objects.requireNonNull(homeData).getCache();

            translateResult = cache.get(cacheKey);
            if (StringUtils.isBlank(translateResult)) {
                TranslateHandler translateHandler = App.getConfigure().getTranslateHandler();
                translateResult = translateHandler.handleTranslate(query, "auto", "zh");
                translateCache.put(cacheKey, translateResult);
                cache.put(cacheKey, translateResult);
            }
        }

        this.busNotifyInfo("Translator", "Translate Result", translateResult, event);
    }

    private String populateCacheKey(String query) {
        String key = query + "." + "zh";

        return key;
    }

    // @formatter:off
    private void busNotifyInfo(
            @NotNull String groupId,
            @NotNull @NlsContexts.NotificationTitle String title,
            @NotNull @NlsContexts.NotificationContent String content,
            @NotNull AnActionEvent event) {
        this.busNotify(groupId, title, content, NotificationType.INFORMATION, event);
    }

    private void busNotifyWarning(
            @NotNull String groupId,
            @NotNull @NlsContexts.NotificationTitle String title,
            @NotNull @NlsContexts.NotificationContent String content,
            @NotNull AnActionEvent event) {
        this.busNotify(groupId, title, content, NotificationType.WARNING, event);
    }

    private void busNotifyError(
            @NotNull String groupId,
            @NotNull @NlsContexts.NotificationTitle String title,
            @NotNull @NlsContexts.NotificationContent String content,
            @NotNull AnActionEvent event) {
        this.busNotify(groupId, title, content, NotificationType.ERROR, event);
    }

    private void busNotify(
            @NotNull String groupId,
            @NotNull @NlsContexts.NotificationTitle String title,
            @NotNull @NlsContexts.NotificationContent String content,
            @NotNull NotificationType type, @NotNull AnActionEvent event) {
        Notification notification = new Notification(groupId, title, content, type);
        Notifications.Bus.notify(notification, event.getProject());
    }
    // @formatter:on
}
