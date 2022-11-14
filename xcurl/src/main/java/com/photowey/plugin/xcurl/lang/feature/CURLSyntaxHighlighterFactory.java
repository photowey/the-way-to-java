package com.photowey.plugin.xcurl.lang.feature;

import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@code CURLSyntaxHighlighterFactory}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class CURLSyntaxHighlighterFactory extends SyntaxHighlighterFactory {

    @Override
    public @NotNull
    SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
        return new CURLSyntaxHighlighter();
    }
}