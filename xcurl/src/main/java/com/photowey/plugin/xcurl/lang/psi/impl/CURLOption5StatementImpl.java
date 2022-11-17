// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.photowey.plugin.xcurl.lang.psi.CURLOption5;
import com.photowey.plugin.xcurl.lang.psi.CURLOption5Statement;
import com.photowey.plugin.xcurl.lang.psi.CURLVisitor;
import org.jetbrains.annotations.NotNull;

import static com.photowey.plugin.xcurl.lang.CURLTypes.METHOD;

public class CURLOption5StatementImpl extends ASTWrapperPsiElement implements CURLOption5Statement {

  public CURLOption5StatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitOption5Statement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CURLOption5 getOption5() {
    return findNotNullChildByClass(CURLOption5.class);
  }

  @Override
  @NotNull
  public PsiElement getMethod() {
    return findNotNullChildByType(METHOD);
  }

}
