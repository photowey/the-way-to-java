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
