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
package com.photowey.plugin.xcurl.lang.feature;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.photowey.plugin.xcurl.icon.XCURLIcons;
import com.photowey.plugin.xcurl.lang.XCURLFileType;
import com.photowey.plugin.xcurl.lang.psi.XCURLCommand;
import com.photowey.plugin.xcurl.lang.psi.XCURLFile;
import com.photowey.plugin.xcurl.util.ApiUtils;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@code XCURLRelatedItemLineMarkerProvider}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class XCURLRelatedItemLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        if (!(element instanceof PsiMethod)) {
            return;
        }
        PsiMethod psiMethod = (PsiMethod) element;
        String path = ApiUtils.getPath(psiMethod);
        if (path.equals("")) {
            return;
        }
        Project project = element.getProject();
        List<XCURLCommand> commands = findCommand(project, path);
        if (commands.size() > 0) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(XCURLIcons.ICON)
                            .setTargets(commands)
                            .setTooltipText("Navigate to CURL");

            result.add(builder.createLineMarkerInfo(element));
        }
    }

    public static List<XCURLCommand> findCommand(Project project, String key) {
        List<XCURLCommand> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(XCURLFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            XCURLFile xcurlFile = (XCURLFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (xcurlFile == null) {
                continue;
            }
            XCURLCommand[] commands = PsiTreeUtil.getChildrenOfType(xcurlFile, XCURLCommand.class);
            if (commands == null) {
                continue;
            }
            for (XCURLCommand command : commands) {
                try {
                    String url = command.getUrl().getText();
                    String path = new URL(url).getPath();
                    if (key.equals(path)) {
                        result.add(command);
                    }
                } catch (Exception ignored) {
                }
            }
        }

        return result;
    }
}