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
package com.photowey.translator.listener;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.photowey.translator.constant.TranslatorConstants;
import com.photowey.translator.extension.TranslatorSettings;
import com.photowey.translator.home.Home;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * {@code TranslatorSettingListener}
 *
 * @author photowey
 * @date 2022/11/14
 * @since 1.0.0
 */
public class TranslatorSettingListener implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        Home.load();

        TranslatorSettings translatorSettings = TranslatorSettings.getInstance();

        String appId = translatorSettings.getAppId();
        String appSecret = translatorSettings.getAppSecret();

        if (StringUtils.isBlank(appId) || StringUtils.isBlank(appSecret)) {
            return;
        }

        Notification notification = new Notification("Print",
                TranslatorConstants.PLUGIN_NAME, "Please set appId,appSecret first", NotificationType.INFORMATION);
        notification.addAction(new OpenTranslatorSettingAction());

        Notifications.Bus.notify(notification, project);
    }

    static class OpenTranslatorSettingAction extends NotificationAction {

        OpenTranslatorSettingAction() {
            super("Open Translator UI");
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification notification) {
            ShowSettingsUtil.getInstance().showSettingsDialog(e.getProject(), TranslatorConstants.PLUGIN_NAME);
            notification.expire();
        }
    }
}