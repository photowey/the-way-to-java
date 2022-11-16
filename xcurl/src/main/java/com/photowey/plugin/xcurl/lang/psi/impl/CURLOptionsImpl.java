/**
 * Copyright © 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.photowey.plugin.xcurl.lang.CURLTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.photowey.plugin.xcurl.lang.psi.*;

public class CURLOptionsImpl extends ASTWrapperPsiElement implements CURLOptions {

  public CURLOptionsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitOptions(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CURLOption1Statement getOption1Statement() {
    return findChildByClass(CURLOption1Statement.class);
  }

  @Override
  @Nullable
  public CURLOption2Statement getOption2Statement() {
    return findChildByClass(CURLOption2Statement.class);
  }

  @Override
  @Nullable
  public CURLOption3Statement getOption3Statement() {
    return findChildByClass(CURLOption3Statement.class);
  }

  @Override
  @Nullable
  public CURLOption4Statement getOption4Statement() {
    return findChildByClass(CURLOption4Statement.class);
  }

  @Override
  @Nullable
  public CURLOption5Statement getOption5Statement() {
    return findChildByClass(CURLOption5Statement.class);
  }

  @Override
  @Nullable
  public CURLOption6Statement getOption6Statement() {
    return findChildByClass(CURLOption6Statement.class);
  }

  @Override
  @Nullable
  public CURLOption7Statement getOption7Statement() {
    return findChildByClass(CURLOption7Statement.class);
  }

  @Override
  @Nullable
  public CURLOption8Statement getOption8Statement() {
    return findChildByClass(CURLOption8Statement.class);
  }

}
