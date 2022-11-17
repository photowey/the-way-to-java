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

public class XCURLOptionsImpl extends ASTWrapperPsiElement implements XCURLOptions {

  public XCURLOptionsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XCURLVisitor visitor) {
    visitor.visitOptions(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XCURLVisitor) accept((XCURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public XCURLNewLineStatement getNewLineStatement() {
    return findChildByClass(XCURLNewLineStatement.class);
  }

  @Override
  @Nullable
  public XCURLOption1Statement getOption1Statement() {
    return findChildByClass(XCURLOption1Statement.class);
  }

  @Override
  @Nullable
  public XCURLOption2Statement getOption2Statement() {
    return findChildByClass(XCURLOption2Statement.class);
  }

  @Override
  @Nullable
  public XCURLOption3Statement getOption3Statement() {
    return findChildByClass(XCURLOption3Statement.class);
  }

  @Override
  @Nullable
  public XCURLOption4Statement getOption4Statement() {
    return findChildByClass(XCURLOption4Statement.class);
  }

  @Override
  @Nullable
  public XCURLOption5Statement getOption5Statement() {
    return findChildByClass(XCURLOption5Statement.class);
  }

  @Override
  @Nullable
  public XCURLOption6Statement getOption6Statement() {
    return findChildByClass(XCURLOption6Statement.class);
  }

  @Override
  @Nullable
  public XCURLOption7Statement getOption7Statement() {
    return findChildByClass(XCURLOption7Statement.class);
  }

  @Override
  @Nullable
  public XCURLOption8Statement getOption8Statement() {
    return findChildByClass(XCURLOption8Statement.class);
  }

}
