// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.photowey.plugin.xcurl.lang.psi.CURLCommand;
import com.photowey.plugin.xcurl.lang.psi.CURLOptions;
import com.photowey.plugin.xcurl.lang.psi.CURLVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.photowey.plugin.xcurl.lang.CURLTypes.URL;

public class CURLCommandImpl extends ASTWrapperPsiElement implements CURLCommand {

  public CURLCommandImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitCommand(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CURLOptions> getOptionsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CURLOptions.class);
  }

  @Override
  @NotNull
  public PsiElement getUrl() {
    return findNotNullChildByType(URL);
  }

}
