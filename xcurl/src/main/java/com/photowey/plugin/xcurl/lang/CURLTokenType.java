package com.photowey.plugin.xcurl.lang;

import com.intellij.psi.tree.IElementType;

/**
 * {@code CURLTokenType}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class CURLTokenType extends IElementType {

    public CURLTokenType(String element) {
        super(element, XCURLanguage.getInstance());
    }

    @Override
    public String toString() {
        return "CURLTokenType." + super.toString();
    }
}