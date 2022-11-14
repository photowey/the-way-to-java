package com.photowey.plugin.xcurl.lang;

import com.intellij.psi.tree.IElementType;

/**
 * {@code CURLElementType}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
class CURLElementType extends IElementType {

    public CURLElementType(String element) {
        super(element, XCURLanguage.getInstance());
    }
}