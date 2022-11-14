// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CURLCommand extends PsiElement {

  @NotNull
  List<CURLOptions> getOptionsList();

  @NotNull
  PsiElement getUrl();

}
