package com.photowey.plugin.xcurl.lang;

import com.intellij.lang.Language;

/**
 * {@code XCURLanguage}
 *
 * @author photowey
 * @date 2022/11/14
 * @since 1.0.0
 */
public class XCURLanguage extends Language {

    private XCURLanguage() {
        super("XCURL");
    }

    private static class XCURLanguageFactory {
        private static final XCURLanguage INSTANCE = new XCURLanguage();
    }

    public static XCURLanguage getInstance() {
        return XCURLanguageFactory.INSTANCE;
    }
}