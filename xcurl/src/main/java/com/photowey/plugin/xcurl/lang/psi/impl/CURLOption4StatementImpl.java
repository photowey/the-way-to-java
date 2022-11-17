// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.photowey.plugin.xcurl.lang.psi.CURLOption4;
import com.photowey.plugin.xcurl.lang.psi.CURLOption4Statement;
import com.photowey.plugin.xcurl.lang.psi.CURLVisitor;
import org.jetbrains.annotations.NotNull;

public class CURLOption4StatementImpl extends ASTWrapperPsiElement implements CURLOption4Statement {

  public CURLOption4StatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitOption4Statement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CURLOption4 getOption4() {
    return findNotNullChildByClass(CURLOption4.class);
  }

}
