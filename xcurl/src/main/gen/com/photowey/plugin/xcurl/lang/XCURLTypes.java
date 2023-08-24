/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.plugin.xcurl.lang;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.photowey.plugin.xcurl.lang.psi.XCURLElementType;
import com.photowey.plugin.xcurl.lang.psi.XCURLTokenType;
import com.photowey.plugin.xcurl.lang.psi.impl.*;

public interface XCURLTypes {

  IElementType COMMAND = new XCURLElementType("COMMAND");
  IElementType KV = new XCURLElementType("KV");
  IElementType OPTIONS = new XCURLElementType("OPTIONS");
  IElementType OPTION_1 = new XCURLElementType("OPTION_1");
  IElementType OPTION_1_STATEMENT = new XCURLElementType("OPTION_1_STATEMENT");
  IElementType OPTION_2 = new XCURLElementType("OPTION_2");
  IElementType OPTION_2_STATEMENT = new XCURLElementType("OPTION_2_STATEMENT");
  IElementType OPTION_3 = new XCURLElementType("OPTION_3");
  IElementType OPTION_3_STATEMENT = new XCURLElementType("OPTION_3_STATEMENT");
  IElementType OPTION_4 = new XCURLElementType("OPTION_4");
  IElementType OPTION_4_STATEMENT = new XCURLElementType("OPTION_4_STATEMENT");
  IElementType OPTION_5 = new XCURLElementType("OPTION_5");
  IElementType OPTION_5_STATEMENT = new XCURLElementType("OPTION_5_STATEMENT");
  IElementType OPTION_6 = new XCURLElementType("OPTION_6");
  IElementType OPTION_6_STATEMENT = new XCURLElementType("OPTION_6_STATEMENT");
  IElementType OPTION_7 = new XCURLElementType("OPTION_7");
  IElementType OPTION_7_STATEMENT = new XCURLElementType("OPTION_7_STATEMENT");
  IElementType OPTION_8 = new XCURLElementType("OPTION_8");
  IElementType OPTION_8_STATEMENT = new XCURLElementType("OPTION_8_STATEMENT");

  IElementType BASIC_STRING = new XCURLTokenType("BASIC_STRING");
  IElementType COMMENT = new XCURLTokenType("COMMENT");
  IElementType CURL = new XCURLTokenType("CURL");
  IElementType METHOD = new XCURLTokenType("METHOD");
  IElementType NEWLINE = new XCURLTokenType("NEWLINE");
  IElementType OPTION = new XCURLTokenType("OPTION");
  IElementType QUOTED_STRING = new XCURLTokenType("QUOTED_STRING");
  IElementType URL = new XCURLTokenType("URL");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == COMMAND) {
        return new XCURLCommandImpl(node);
      }
      else if (type == KV) {
        return new XCURLKvImpl(node);
      }
      else if (type == OPTIONS) {
        return new XCURLOptionsImpl(node);
      }
      else if (type == OPTION_1) {
        return new XCURLOption1Impl(node);
      }
      else if (type == OPTION_1_STATEMENT) {
        return new XCURLOption1StatementImpl(node);
      }
      else if (type == OPTION_2) {
        return new XCURLOption2Impl(node);
      }
      else if (type == OPTION_2_STATEMENT) {
        return new XCURLOption2StatementImpl(node);
      }
      else if (type == OPTION_3) {
        return new XCURLOption3Impl(node);
      }
      else if (type == OPTION_3_STATEMENT) {
        return new XCURLOption3StatementImpl(node);
      }
      else if (type == OPTION_4) {
        return new XCURLOption4Impl(node);
      }
      else if (type == OPTION_4_STATEMENT) {
        return new XCURLOption4StatementImpl(node);
      }
      else if (type == OPTION_5) {
        return new XCURLOption5Impl(node);
      }
      else if (type == OPTION_5_STATEMENT) {
        return new XCURLOption5StatementImpl(node);
      }
      else if (type == OPTION_6) {
        return new XCURLOption6Impl(node);
      }
      else if (type == OPTION_6_STATEMENT) {
        return new XCURLOption6StatementImpl(node);
      }
      else if (type == OPTION_7) {
        return new XCURLOption7Impl(node);
      }
      else if (type == OPTION_7_STATEMENT) {
        return new XCURLOption7StatementImpl(node);
      }
      else if (type == OPTION_8) {
        return new XCURLOption8Impl(node);
      }
      else if (type == OPTION_8_STATEMENT) {
        return new XCURLOption8StatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
