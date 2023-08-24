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
package com.photowey.mybatis.in.action.plugin.chatgpt;

import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * {@code PluginLoader}
 *
 * @author photowey
 * @date 2023/06/03
 * @since 1.0.0
 */
@Component
public class PluginLoader {

    public void loadPlugins() {
        String pluginDirectoryPath = "plugins"; // 插件类的jar包存放目录

        File pluginDirectory = new File(pluginDirectoryPath);
        if (!pluginDirectory.exists()) {
            return;
        }

        File[] pluginFiles = pluginDirectory.listFiles();
        if (pluginFiles == null) {
            return;
        }

        for (File pluginFile : pluginFiles) {
            if (pluginFile.isFile() && pluginFile.getName().endsWith(".jar")) {
                loadPlugin(pluginFile);
            }
        }
    }

    private void loadPlugin(File pluginFile) {
        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{pluginFile.toURI().toURL()});
            Class<?> pluginClass = classLoader.loadClass("com.example.MyPluginClass"); // 插件类的全限定名
            // 在这里可以调用插件类的方法或进行其他操作
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}