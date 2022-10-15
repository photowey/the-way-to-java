package com.photowey.translator.handler;

/**
 * {@code TranslateHandler}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public interface TranslateHandler {

    String handleTranslate(String query, String from, String to);
}
