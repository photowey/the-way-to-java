// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CURLCommand extends PsiElement {

  @NotNull
  List<CURLOptions> getOptionsList();

  @NotNull
  PsiElement getUrl();

}
