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
import static com.photowey.plugin.xcurl.lang.XCURLTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.photowey.plugin.xcurl.lang.psi.*;

public class XCURLOption3StatementImpl extends ASTWrapperPsiElement implements XCURLOption3Statement {

  public XCURLOption3StatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XCURLVisitor visitor) {
    visitor.visitOption3Statement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XCURLVisitor) accept((XCURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XCURLKv getKv() {
    return findNotNullChildByClass(XCURLKv.class);
  }

  @Override
  @NotNull
  public XCURLOption3 getOption3() {
    return findNotNullChildByClass(XCURLOption3.class);
  }

}
