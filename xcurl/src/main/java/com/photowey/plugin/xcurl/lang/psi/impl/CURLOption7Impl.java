// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.photowey.plugin.xcurl.lang.psi.CURLOption7;
import com.photowey.plugin.xcurl.lang.psi.CURLVisitor;
import org.jetbrains.annotations.NotNull;

public class CURLOption7Impl extends ASTWrapperPsiElement implements CURLOption7 {

  public CURLOption7Impl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitOption7(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

}
