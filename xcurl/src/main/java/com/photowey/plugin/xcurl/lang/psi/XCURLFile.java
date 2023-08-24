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
package com.photowey.plugin.xcurl.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.photowey.plugin.xcurl.lang.XCURLFileType;
import com.photowey.plugin.xcurl.lang.XCURLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * {@code XCURLFile}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class XCURLFile extends PsiFileBase {

    public XCURLFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, XCURLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return XCURLFileType.INSTANCE;
    }

    @Override
    public String toString() {
        String et = super.toString();
        return et + ":" + this.getName();
    }
}