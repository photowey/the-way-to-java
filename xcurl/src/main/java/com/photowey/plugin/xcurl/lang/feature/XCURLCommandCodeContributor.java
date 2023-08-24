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
package com.photowey.plugin.xcurl.lang.feature;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.util.ProcessingContext;
import com.photowey.plugin.xcurl.icon.XCURLIcons;
import com.photowey.plugin.xcurl.lang.psi.XCURLFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@code XCURLCommandCodeContributor}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class XCURLCommandCodeContributor extends CompletionContributor {

    private final static List<String> completionItems = List.of(
            "curl", "CURL",
            "get",
            "-anyauth",
            "--append", "-A",
            "--user",
            "--request",
            "--user-agent", "-H",
            "--header",
            "--output", "-o",
            "--data", "-d",
            "http://", "https://",
            "GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE"
    );

    public XCURLCommandCodeContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().inFile(StandardPatterns.instanceOf(XCURLFile.class)),
                new XCURLCommandCompletionProvider()
        );
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        int offset = parameters.getOffset();
        Document document = parameters.getEditor().getDocument();
        int lineStartOffset = document.getLineStartOffset(document.getLineNumber(offset));
        String prefix = document.getText(TextRange.create(lineStartOffset, offset));
        int lastSpace = prefix.lastIndexOf(' ') == -1 ? 0 : prefix.lastIndexOf(' ') + 1;
        super.fillCompletionVariants(parameters, result.withPrefixMatcher(prefix.substring(lastSpace)));
    }

    static class XCURLCommandCompletionProvider extends CompletionProvider<CompletionParameters> {
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context,
                                      @NotNull CompletionResultSet result) {
            for (String item : completionItems) {
                result.addElement(LookupElementBuilder
                        .create(item)
                        .withPresentableText(item)
                        .withTailText(item)
                        .bold()
                        .withTypeText(item)
                        .withIcon(XCURLIcons.ICON)
                );
            }
        }
    }
}
