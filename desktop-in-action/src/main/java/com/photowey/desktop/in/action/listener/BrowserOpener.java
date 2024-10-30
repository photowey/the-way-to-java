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
package com.photowey.desktop.in.action.listener;

import com.photowey.desktop.in.action.core.event.CustomApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

/**
 * {@code BrowserOpener}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/30
 */
@Component
public class BrowserOpener implements ApplicationListener<CustomApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(CustomApplicationStartedEvent event) {
        openBrowser("http://localhost:9527/healthz");
    }

    private void openBrowser(String url) {
        if (this.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private boolean isDesktopSupported() {
        // Desktop.isDesktopSupported()
        return !Boolean.parseBoolean(System.getProperty("java.awt.headless", "true"));
    }
}
