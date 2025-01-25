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
package com.photowey.captcha.in.action.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * {@code CaptchaTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/25
 */
@Slf4j
class CaptchaTest {

    @Test
    void testGenerate() {
        // 去掉部分易混淆的字符 0|o 1|l|i b|h ...
        String baseStr = "234567890abcdefgkmnpqrtuvwxtzABCDEFGHJKLMNPQRSTUVWXYZ";
        RandomGenerator generator = new RandomGenerator(baseStr, 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(130, 48, generator, 100);

        String code = lineCaptcha.getCode();
        log.info("the captcha code: \n{}", code);

        String base64ImageString = "data:image/png;base64," + lineCaptcha.getImageBase64();
        log.info("the captcha code base64: \n{}", base64ImageString);

        String outputImagePath = code + ".png";
        convertBase64ToImage(base64ImageString, outputImagePath);
        simulateHtmlOutput(code, base64ImageString);
    }

    public static void convertBase64ToImage(String base64ImageString, String outputPath) {
        base64ImageString = tryTrim(base64ImageString);
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
        write(imageBytes, outputPath);
    }

    public static void write(byte[] content, String outputPath) {
        try {
            try (OutputStream outputStream = new FileOutputStream(outputPath)) {
                outputStream.write(content);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String tryTrim(String base64ImageString) {
        final String prefix = "data:image/png;base64,";
        if (base64ImageString.startsWith(prefix)) {
            base64ImageString = base64ImageString.substring(prefix.length());
        }

        return base64ImageString;
    }

    private static void simulateHtmlOutput(String code, String base64ImageString) {
        String buf = "<!DOCTYPE html>" +
            "<meta charset=\"utf-8\"/>" +
            "<html>" +
            "<body>" +
            "<p>Captcha：</p>" +
            "<p><strong>Code：</strong>" + code + "</p>" +
            "<p><strong>Image：</strong></p>" +
            "<img src='" + base64ImageString + "' alt='验证码图片'>" +
            "</body>" +
            "</html>";

        write(buf.getBytes(StandardCharsets.UTF_8), code + ".html");
    }
}
