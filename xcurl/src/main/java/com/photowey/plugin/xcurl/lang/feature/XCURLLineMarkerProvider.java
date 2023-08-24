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

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.execution.lineMarker.RunLineMarkerProvider;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.photowey.plugin.xcurl.action.XCURLRunAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * {@code XCURLLineMarkerProvider}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class XCURLLineMarkerProvider extends RunLineMarkerProvider {
    private static final String XCURL_COMMAND = "curl";

    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        if (!(element.getText().equalsIgnoreCase(XCURL_COMMAND))) {
            return null;
        }
        return new RunLineMarkerInfo(element, AllIcons.Actions.Execute);
    }

    static class RunLineMarkerInfo extends LineMarkerInfo<PsiElement> {

        RunLineMarkerInfo(PsiElement element, Icon icon) {
            super(element, element.getTextRange(), icon, psiElement -> "Run", null, GutterIconRenderer.Alignment.CENTER, () -> "run");
        }

        @Override
        public GutterIconRenderer createGutterRenderer() {
            return new LineMarkerGutterIconRenderer<>(this) {
                @Override
                public AnAction getClickAction() {
                    // Override click.action
                    return new XCURLRunAction(this.getLineMarkerInfo().getElement());
                }
            };
        }
    }
}
