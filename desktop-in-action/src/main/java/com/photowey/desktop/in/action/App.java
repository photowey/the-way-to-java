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
package com.photowey.desktop.in.action;

import com.photowey.desktop.in.action.core.event.ApplicationStartedLocalEvent;
import com.photowey.print.in.action.printer.AppPrinter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StopWatch;

/**
 * {@code App}
 *
 * @author photowey
 * @date 2024/10/30
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        StopWatch watch = new StopWatch("setup");
        watch.start();

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(App.class)
            .bannerMode(Banner.Mode.CONSOLE)
            .logStartupInfo(true)
            .build(args)
            .run(args);

        watch.stop();
        applicationContext.publishEvent(new ApplicationStartedLocalEvent());
        AppPrinter.print(applicationContext, false);

        log.info("Report: webapp started, took [{}] ms", watch.getTotalTimeMillis());
    }
}
