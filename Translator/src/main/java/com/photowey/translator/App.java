package com.photowey.translator;

import com.photowey.translator.config.TranslatorConfigure;

/**
 * {@code App}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class App {

    private static class TranslatorConfigureFactory {
        private static final TranslatorConfigure CONFIGURE = new TranslatorConfigure();

        private static TranslatorConfigure createTranslatorConfigure() {
            return TranslatorConfigureFactory.CONFIGURE;
        }
    }

    public static TranslatorConfigure getConfigure() {
        return TranslatorConfigureFactory.createTranslatorConfigure();
    }
}
