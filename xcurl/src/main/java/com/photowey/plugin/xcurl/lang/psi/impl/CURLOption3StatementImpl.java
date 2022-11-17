// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.photowey.plugin.xcurl.lang.psi.CURLKv;
import com.photowey.plugin.xcurl.lang.psi.CURLOption3;
import com.photowey.plugin.xcurl.lang.psi.CURLOption3Statement;
import com.photowey.plugin.xcurl.lang.psi.CURLVisitor;
import org.jetbrains.annotations.NotNull;

public class CURLOption3StatementImpl extends ASTWrapperPsiElement implements CURLOption3Statement {

  public CURLOption3StatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitOption3Statement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CURLKv getKv() {
    return findNotNullChildByClass(CURLKv.class);
  }

  @Override
  @NotNull
  public CURLOption3 getOption3() {
    return findNotNullChildByClass(CURLOption3.class);
  }

}
