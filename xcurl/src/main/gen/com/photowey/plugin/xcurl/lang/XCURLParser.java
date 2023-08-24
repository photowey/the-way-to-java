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

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.photowey.plugin.xcurl.lang.XCURLTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class XCURLParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return XCURLFile(b, l + 1);
  }

  /* ********************************************************** */
  // CURL [OPTIONS*] URL
  public static boolean COMMAND(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMMAND")) return false;
    if (!nextTokenIs(b, CURL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CURL);
    r = r && COMMAND_1(b, l + 1);
    r = r && consumeToken(b, URL);
    exit_section_(b, m, COMMAND, r);
    return r;
  }

  // [OPTIONS*]
  private static boolean COMMAND_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMMAND_1")) return false;
    COMMAND_1_0(b, l + 1);
    return true;
  }

  // OPTIONS*
  private static boolean COMMAND_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMMAND_1_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!OPTIONS(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "COMMAND_1_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // BASIC_STRING':'BASIC_STRING
  public static boolean KV(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "KV")) return false;
    if (!nextTokenIs(b, BASIC_STRING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BASIC_STRING);
    r = r && consumeToken(b, ":");
    r = r && consumeToken(b, BASIC_STRING);
    exit_section_(b, m, KV, r);
    return r;
  }

  /* ********************************************************** */
  // '-a'|'--append'
  public static boolean OPTION1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_1, "<option 1>");
    r = consumeToken(b, "-a");
    if (!r) r = consumeToken(b, "--append");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '-A'|'--user-agent'
  public static boolean OPTION2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION2")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_2, "<option 2>");
    r = consumeToken(b, "-A");
    if (!r) r = consumeToken(b, "--user-agent");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('-anyauth') ('-u' | '--user')
  public static boolean OPTION3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION3")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_3, "<option 3>");
    r = OPTION3_0(b, l + 1);
    r = r && OPTION3_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ('-anyauth')
  private static boolean OPTION3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "-anyauth");
    exit_section_(b, m, null, r);
    return r;
  }

  // '-u' | '--user'
  private static boolean OPTION3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION3_1")) return false;
    boolean r;
    r = consumeToken(b, "-u");
    if (!r) r = consumeToken(b, "--user");
    return r;
  }

  /* ********************************************************** */
  // '-G' | '--get'
  public static boolean OPTION4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION4")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_4, "<option 4>");
    r = consumeToken(b, "-G");
    if (!r) r = consumeToken(b, "--get");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '-X'|'--request'
  public static boolean OPTION5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION5")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_5, "<option 5>");
    r = consumeToken(b, "-X");
    if (!r) r = consumeToken(b, "--request");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '-H'|'--header'
  public static boolean OPTION6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION6")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_6, "<option 6>");
    r = consumeToken(b, "-H");
    if (!r) r = consumeToken(b, "--header");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '-o'|'--output'
  public static boolean OPTION7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION7")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_7, "<option 7>");
    r = consumeToken(b, "-o");
    if (!r) r = consumeToken(b, "--output");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '-d'|'--data'
  public static boolean OPTION8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTION8")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_8, "<option 8>");
    r = consumeToken(b, "-d");
    if (!r) r = consumeToken(b, "--data");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Option1Statement | Option2Statement | Option3Statement | Option4Statement | Option5Statement | Option6Statement| Option7Statement| Option8Statement
  public static boolean OPTIONS(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OPTIONS")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTIONS, "<options>");
    r = Option1Statement(b, l + 1);
    if (!r) r = Option2Statement(b, l + 1);
    if (!r) r = Option3Statement(b, l + 1);
    if (!r) r = Option4Statement(b, l + 1);
    if (!r) r = Option5Statement(b, l + 1);
    if (!r) r = Option6Statement(b, l + 1);
    if (!r) r = Option7Statement(b, l + 1);
    if (!r) r = Option8Statement(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // OPTION1 QUOTED_STRING [NEWLINE?]
  public static boolean Option1Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option1Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_1_STATEMENT, "<option 1 statement>");
    r = OPTION1(b, l + 1);
    r = r && consumeToken(b, QUOTED_STRING);
    r = r && Option1Statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [NEWLINE?]
  private static boolean Option1Statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option1Statement_2")) return false;
    Option1Statement_2_0(b, l + 1);
    return true;
  }

  // NEWLINE?
  private static boolean Option1Statement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option1Statement_2_0")) return false;
    consumeToken(b, NEWLINE);
    return true;
  }

  /* ********************************************************** */
  // OPTION2 QUOTED_STRING [NEWLINE?]
  public static boolean Option2Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option2Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_2_STATEMENT, "<option 2 statement>");
    r = OPTION2(b, l + 1);
    r = r && consumeToken(b, QUOTED_STRING);
    r = r && Option2Statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [NEWLINE?]
  private static boolean Option2Statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option2Statement_2")) return false;
    Option2Statement_2_0(b, l + 1);
    return true;
  }

  // NEWLINE?
  private static boolean Option2Statement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option2Statement_2_0")) return false;
    consumeToken(b, NEWLINE);
    return true;
  }

  /* ********************************************************** */
  // OPTION3 KV [NEWLINE?]
  public static boolean Option3Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option3Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_3_STATEMENT, "<option 3 statement>");
    r = OPTION3(b, l + 1);
    r = r && KV(b, l + 1);
    r = r && Option3Statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [NEWLINE?]
  private static boolean Option3Statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option3Statement_2")) return false;
    Option3Statement_2_0(b, l + 1);
    return true;
  }

  // NEWLINE?
  private static boolean Option3Statement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option3Statement_2_0")) return false;
    consumeToken(b, NEWLINE);
    return true;
  }

  /* ********************************************************** */
  // OPTION4 [NEWLINE?]
  public static boolean Option4Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option4Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_4_STATEMENT, "<option 4 statement>");
    r = OPTION4(b, l + 1);
    r = r && Option4Statement_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [NEWLINE?]
  private static boolean Option4Statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option4Statement_1")) return false;
    Option4Statement_1_0(b, l + 1);
    return true;
  }

  // NEWLINE?
  private static boolean Option4Statement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option4Statement_1_0")) return false;
    consumeToken(b, NEWLINE);
    return true;
  }

  /* ********************************************************** */
  // OPTION5 METHOD [NEWLINE?]
  public static boolean Option5Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option5Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_5_STATEMENT, "<option 5 statement>");
    r = OPTION5(b, l + 1);
    r = r && consumeToken(b, METHOD);
    r = r && Option5Statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [NEWLINE?]
  private static boolean Option5Statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option5Statement_2")) return false;
    Option5Statement_2_0(b, l + 1);
    return true;
  }

  // NEWLINE?
  private static boolean Option5Statement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option5Statement_2_0")) return false;
    consumeToken(b, NEWLINE);
    return true;
  }

  /* ********************************************************** */
  // OPTION6 QUOTED_STRING [NEWLINE?]
  public static boolean Option6Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option6Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_6_STATEMENT, "<option 6 statement>");
    r = OPTION6(b, l + 1);
    r = r && consumeToken(b, QUOTED_STRING);
    r = r && Option6Statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [NEWLINE?]
  private static boolean Option6Statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option6Statement_2")) return false;
    Option6Statement_2_0(b, l + 1);
    return true;
  }

  // NEWLINE?
  private static boolean Option6Statement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option6Statement_2_0")) return false;
    consumeToken(b, NEWLINE);
    return true;
  }

  /* ********************************************************** */
  // OPTION7 QUOTED_STRING [NEWLINE?]
  public static boolean Option7Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option7Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_7_STATEMENT, "<option 7 statement>");
    r = OPTION7(b, l + 1);
    r = r && consumeToken(b, QUOTED_STRING);
    r = r && Option7Statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [NEWLINE?]
  private static boolean Option7Statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option7Statement_2")) return false;
    Option7Statement_2_0(b, l + 1);
    return true;
  }

  // NEWLINE?
  private static boolean Option7Statement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option7Statement_2_0")) return false;
    consumeToken(b, NEWLINE);
    return true;
  }

  /* ********************************************************** */
  // OPTION8 QUOTED_STRING [NEWLINE?]
  public static boolean Option8Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option8Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTION_8_STATEMENT, "<option 8 statement>");
    r = OPTION8(b, l + 1);
    r = r && consumeToken(b, QUOTED_STRING);
    r = r && Option8Statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [NEWLINE?]
  private static boolean Option8Statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option8Statement_2")) return false;
    Option8Statement_2_0(b, l + 1);
    return true;
  }

  // NEWLINE?
  private static boolean Option8Statement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Option8Statement_2_0")) return false;
    consumeToken(b, NEWLINE);
    return true;
  }

  /* ********************************************************** */
  // COMMAND*
  static boolean XCURLFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "XCURLFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!COMMAND(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "XCURLFile", c)) break;
    }
    return true;
  }

}
