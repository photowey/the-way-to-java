package com.photowey.plugin.xcurl.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.photowey.plugin.xcurl.lang.XCURLTypes.*;

%%

%{
  public _XCURLLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _XCURLLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

WHITE_SPACE=[ \t\n\x0B\f\r]+
URL=(http|https?|ftp|file):"//"[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]
QUOTED_STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
BASIC_STRING=[0-9a-zA_Z]*
COMMENT="//".*|"/"\*\*.*\*"/"
OPTION=--append|-a|-A|--user-agent|-anyauth|-u|--user|-G|--get|-X|--request|--header|-H|--output|-o|--data|-d
METHOD=GET| HEAD| POST| PATCH| PUT| DELETE| CONNECT| OPTIONS| TRACE

%%
<YYINITIAL> {
  {WHITE_SPACE}        { return WHITE_SPACE; }

  "CURL"               { return CURL; }

  {WHITE_SPACE}        { return WHITE_SPACE; }
  {URL}                { return URL; }
  {QUOTED_STRING}      { return QUOTED_STRING; }
  {BASIC_STRING}       { return BASIC_STRING; }
  {COMMENT}            { return COMMENT; }
  {OPTION}             { return OPTION; }
  {METHOD}             { return METHOD; }

}

[^] { return BAD_CHARACTER; }
