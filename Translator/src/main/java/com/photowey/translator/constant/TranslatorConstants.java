package com.photowey.translator.constant;

import com.photowey.translator.hash.Hash;

/**
 * {@code TranslatorConstants}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public interface TranslatorConstants {

    String STRING_EMPTY = "";

    String USER_HOME = "user.home";
    String TRANSLATOR_HOME = ".translator";
    String TRANSLATOR_FILE = "translator.json";

    String TRANSLATOR_CONFIG_AES_KEY = Hash.MD5.md5("com.photowey.translator.config.key");

}
