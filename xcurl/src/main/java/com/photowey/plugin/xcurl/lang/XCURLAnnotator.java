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
package com.photowey.plugin.xcurl.lang;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.annotations.NotNull;

/**
 * {@code XCURLAnnotator}
 *
 * @author photowey
 * @date 2022/11/17
 * @since 1.0.0
 */
public class XCURLAnnotator implements Annotator {

    public static final String XCURL_HELP_STR = "CURL -h";
    public static final String XCURL_PREFIX_STR = "CURL";
    public static final String XCURL_SEPARATOR_STR = " ";
    public static final String XCURL_NEW_LINE_STR = "\\";

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof PsiLiteralExpression)) {
            return;
        }

        PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        boolean shouldSkip = (value == null) || !value.startsWith(XCURL_PREFIX_STR);
        if (shouldSkip) {
            return;
        }

        // TODO
    }

}
