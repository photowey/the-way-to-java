package com.photowey.plugin.xcurl.lang.feature;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.photowey.plugin.xcurl.lang.CURLLexerAdapter;
import com.photowey.plugin.xcurl.lang.CURLTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.ide.highlighter.JavaHighlightingColors.STRING;

/**
 * {@code CURLSyntaxHighlighter}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class CURLSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey KEYWORD =
            TextAttributesKey.createTextAttributesKey("keyword", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey VALUE =
            TextAttributesKey.createTextAttributesKey("value", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT =
            TextAttributesKey.createTextAttributesKey("comment", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BAD_CHARACTER =
            TextAttributesKey.createTextAttributesKey("bad_character", HighlighterColors.BAD_CHARACTER);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new CURLLexerAdapter();
    }

    @Override
    public @NotNull
    TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(CURLTypes.CURL) || tokenType.equals(CURLTypes.OPTION) || tokenType.equals(CURLTypes.METHOD)) {
            return KEYWORD_KEYS;
        }
        if (tokenType.equals(CURLTypes.QUOTED_STRING) || tokenType.equals(CURLTypes.BASIC_STRING)) {
            return STRING_KEYS;
        }
        if (tokenType.equals(CURLTypes.COMMENT)) {
            return COMMENT_KEYS;
        }
        if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        }
        return EMPTY_KEYS;
    }
}