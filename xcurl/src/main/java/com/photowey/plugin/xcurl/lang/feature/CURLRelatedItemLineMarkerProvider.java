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
import com.photowey.plugin.xcurl.icon.XCurlIcons;
import com.photowey.plugin.xcurl.lang.XCURLFile;
import com.photowey.plugin.xcurl.lang.XCURLFileType;
import com.photowey.plugin.xcurl.lang.psi.CURLCommand;
import com.photowey.plugin.xcurl.util.ApiUtils;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@code CURLRelatedItemLineMarkerProvider}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class CURLRelatedItemLineMarkerProvider extends RelatedItemLineMarkerProvider {

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
        List<CURLCommand> commands = findCommand(project, path);
        if (commands.size() > 0) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(XCurlIcons.ICON)
                            .setTargets(commands)
                            .setTooltipText("Navigate to CURL");

            result.add(builder.createLineMarkerInfo(element));
        }
    }

    public static List<CURLCommand> findCommand(Project project, String key) {
        List<CURLCommand> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(XCURLFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            XCURLFile xcurlFile = (XCURLFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (xcurlFile == null) {
                continue;
            }
            CURLCommand[] commands = PsiTreeUtil.getChildrenOfType(xcurlFile, CURLCommand.class);
            if (commands == null) {
                continue;
            }
            for (CURLCommand command : commands) {
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