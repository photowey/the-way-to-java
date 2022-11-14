package com.photowey.plugin.xcurl.lang;

import com.intellij.lexer.FlexAdapter;

/**
 * {@code CURLLexerAdapter}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class CURLLexerAdapter extends FlexAdapter {

    public CURLLexerAdapter() {
        super(new _CURLLexer(null));
    }
}