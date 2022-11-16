/**
 * Copyright © 2022 the original author or authors.
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
package com.photowey.plugin.xcurl.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.photowey.plugin.xcurl.icon.XCurlIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * {@code XCURLFileType}
 *
 * @author photowey
 * @date 2022/11/14
 * @since 1.0.0
 */
public class XCURLFileType extends LanguageFileType {

    public static final XCURLFileType INSTANCE = new XCURLFileType();

    public XCURLFileType() {
        super(XCURLanguage.getInstance());
    }

    public static XCURLFileType getInstance() {
        return INSTANCE;
    }

    @Override
    public @NotNull
    String getName() {
        return "XCURL";
    }

    @Override
    public @NotNull
    String getDescription() {
        return "XCURL language file";
    }

    @Override
    public @NotNull
    String getDefaultExtension() {
        return "xcurl";
    }

    @Override
    public @Nullable
    Icon getIcon() {
        return XCurlIcons.ICON;
    }
}