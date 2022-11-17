package com.photowey.plugin.xcurl.lang;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@code XCURLFoldingBuilder}
 *
 * @author photowey
 * @date 2022/11/17
 * @since 1.0.0
 */
public class XCURLFoldingBuilder extends FoldingBuilderEx implements DumbAware {

    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        FoldingGroup group = FoldingGroup.newGroup(XCURLAnnotator.XCURL_PREFIX_STR);
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        Collection<PsiLiteralExpression> literalExpressions =
                PsiTreeUtil.findChildrenOfType(root, PsiLiteralExpression.class);

        for (final PsiLiteralExpression literalExpression : literalExpressions) {
            String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
            if (value != null && value.startsWith(XCURLAnnotator.XCURL_PREFIX_STR) && value.startsWith(XCURLAnnotator.XCURL_NEW_LINE_STR)) {
                // TODO
                // TextRange textRange = new TextRange(literalExpression.getTextRange().getStartOffset() + 1, literalExpression.getTextRange().getEndOffset() - 1);
                // descriptors.add(new FoldingDescriptor(literalExpression.getNode(), textRange, group));
            }
        }

        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        String retTxt = node.getText();
        if (node.getPsi() instanceof PsiLiteralExpression) {
            PsiLiteralExpression nodeElement = (PsiLiteralExpression) node.getPsi();
            String place = ((String) nodeElement.getValue());
            return place == null ? retTxt : place.replaceAll("\n", "\\n").replaceAll("\"", "\\\\\"");
        }

        return retTxt;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }

}
