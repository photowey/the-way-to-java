package com.photowey.plugin.xcurl.lang;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

/**
 * {@code XCURLFile}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class XCURLFile extends PsiFileBase {

    protected XCURLFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, XCURLanguage.getInstance());
    }

    @Override
    public @NotNull FileType getFileType() {
        return XCURLFileType.INSTANCE;
    }
}