// This is a generated file. Not intended for manual editing.
package com.photowey.plugin.xcurl.lang;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.photowey.plugin.xcurl.lang.psi.impl.*;

public interface CURLTypes {

  IElementType COMMAND = new CURLElementType("COMMAND");
  IElementType KV = new CURLElementType("KV");
  IElementType OPTIONS = new CURLElementType("OPTIONS");
  IElementType OPTION_1 = new CURLElementType("OPTION_1");
  IElementType OPTION_1_STATEMENT = new CURLElementType("OPTION_1_STATEMENT");
  IElementType OPTION_2 = new CURLElementType("OPTION_2");
  IElementType OPTION_2_STATEMENT = new CURLElementType("OPTION_2_STATEMENT");
  IElementType OPTION_3 = new CURLElementType("OPTION_3");
  IElementType OPTION_3_STATEMENT = new CURLElementType("OPTION_3_STATEMENT");
  IElementType OPTION_4 = new CURLElementType("OPTION_4");
  IElementType OPTION_4_STATEMENT = new CURLElementType("OPTION_4_STATEMENT");
  IElementType OPTION_5 = new CURLElementType("OPTION_5");
  IElementType OPTION_5_STATEMENT = new CURLElementType("OPTION_5_STATEMENT");
  IElementType OPTION_6 = new CURLElementType("OPTION_6");
  IElementType OPTION_6_STATEMENT = new CURLElementType("OPTION_6_STATEMENT");
  IElementType OPTION_7 = new CURLElementType("OPTION_7");
  IElementType OPTION_7_STATEMENT = new CURLElementType("OPTION_7_STATEMENT");
  IElementType OPTION_8 = new CURLElementType("OPTION_8");
  IElementType OPTION_8_STATEMENT = new CURLElementType("OPTION_8_STATEMENT");

  IElementType BASIC_STRING = new CURLTokenType("BASIC_STRING");
  IElementType COMMENT = new CURLTokenType("COMMENT");
  IElementType CURL = new CURLTokenType("CURL");
  IElementType METHOD = new CURLTokenType("METHOD");
  IElementType OPTION = new CURLTokenType("OPTION");
  IElementType QUOTED_STRING = new CURLTokenType("QUOTED_STRING");
  IElementType URL = new CURLTokenType("URL");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == COMMAND) {
        return new CURLCommandImpl(node);
      }
      else if (type == KV) {
        return new CURLKvImpl(node);
      }
      else if (type == OPTIONS) {
        return new CURLOptionsImpl(node);
      }
      else if (type == OPTION_1) {
        return new CURLOption1Impl(node);
      }
      else if (type == OPTION_1_STATEMENT) {
        return new CURLOption1StatementImpl(node);
      }
      else if (type == OPTION_2) {
        return new CURLOption2Impl(node);
      }
      else if (type == OPTION_2_STATEMENT) {
        return new CURLOption2StatementImpl(node);
      }
      else if (type == OPTION_3) {
        return new CURLOption3Impl(node);
      }
      else if (type == OPTION_3_STATEMENT) {
        return new CURLOption3StatementImpl(node);
      }
      else if (type == OPTION_4) {
        return new CURLOption4Impl(node);
      }
      else if (type == OPTION_4_STATEMENT) {
        return new CURLOption4StatementImpl(node);
      }
      else if (type == OPTION_5) {
        return new CURLOption5Impl(node);
      }
      else if (type == OPTION_5_STATEMENT) {
        return new CURLOption5StatementImpl(node);
      }
      else if (type == OPTION_6) {
        return new CURLOption6Impl(node);
      }
      else if (type == OPTION_6_STATEMENT) {
        return new CURLOption6StatementImpl(node);
      }
      else if (type == OPTION_7) {
        return new CURLOption7Impl(node);
      }
      else if (type == OPTION_7_STATEMENT) {
        return new CURLOption7StatementImpl(node);
      }
      else if (type == OPTION_8) {
        return new CURLOption8Impl(node);
      }
      else if (type == OPTION_8_STATEMENT) {
        return new CURLOption8StatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
