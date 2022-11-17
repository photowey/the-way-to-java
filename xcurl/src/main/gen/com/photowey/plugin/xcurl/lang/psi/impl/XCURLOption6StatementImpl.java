// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.photowey.plugin.xcurl.lang.XCURLTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.photowey.plugin.xcurl.lang.psi.*;

public class XCURLOption6StatementImpl extends ASTWrapperPsiElement implements XCURLOption6Statement {

  public XCURLOption6StatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XCURLVisitor visitor) {
    visitor.visitOption6Statement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XCURLVisitor) accept((XCURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XCURLOption6 getOption6() {
    return findNotNullChildByClass(XCURLOption6.class);
  }

  @Override
  @NotNull
  public PsiElement getQuotedString() {
    return findNotNullChildByType(QUOTED_STRING);
  }

}
