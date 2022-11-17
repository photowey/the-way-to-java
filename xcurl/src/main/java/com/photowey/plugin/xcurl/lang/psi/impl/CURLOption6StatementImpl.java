// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.photowey.plugin.xcurl.lang.psi.CURLOption6;
import com.photowey.plugin.xcurl.lang.psi.CURLOption6Statement;
import com.photowey.plugin.xcurl.lang.psi.CURLVisitor;
import org.jetbrains.annotations.NotNull;

import static com.photowey.plugin.xcurl.lang.CURLTypes.QUOTED_STRING;

public class CURLOption6StatementImpl extends ASTWrapperPsiElement implements CURLOption6Statement {

  public CURLOption6StatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitOption6Statement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CURLOption6 getOption6() {
    return findNotNullChildByClass(CURLOption6.class);
  }

  @Override
  @NotNull
  public PsiElement getQuotedString() {
    return findNotNullChildByType(QUOTED_STRING);
  }

}
