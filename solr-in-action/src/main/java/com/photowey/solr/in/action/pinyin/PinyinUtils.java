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
package com.photowey.solr.in.action.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * {@code PinyinUtils}
 *
 * @author photowey
 * @date 2022/03/20
 * @since 1.0.0
 */
public final class PinyinUtils {

    private PinyinUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static String toPinYin(String word) {
        return toPinYin(word, "", PinYinChronoUnit.LOWER_CASE);
    }

    public static String toPinYin(String word, PinYinChronoUnit chronoUnit) {
        return toPinYin(word, "", chronoUnit);
    }

    public static String toPinYin(String word, String separator) {
        return toPinYin(word, separator, PinYinChronoUnit.LOWER_CASE);
    }

    public static String toPinYin(String word, String separator, PinYinChronoUnit chronoUnit) {
        try {
            if (null == word || word.trim().length() == 0) {
                return "";
            }
            HanyuPinyinOutputFormat format = populatePinyinOutputFormat(chronoUnit);

            StringBuffer pinyinBuffer = new StringBuffer();
            String[] pinyins = new String[word.length()];
            String first = "";

            for (int i = 0; i < word.length(); i++) {
                char charAt = word.charAt(i);
                if ((int) charAt <= 128) {
                    pinyinBuffer.append(charAt);
                } else {
                    pinyins = PinyinHelper.toHanyuPinyinStringArray(charAt, format);
                    if (pinyins == null) {
                        pinyinBuffer.append(charAt);
                    } else {
                        first = pinyins[0];
                        if (PinYinChronoUnit.FIRST_UPPER_CASE.equals(chronoUnit)) {
                            first = pinyins[0].toUpperCase().charAt(0) + first.substring(1);
                        }
                        pinyinBuffer.append(first).append(i == word.length() - 1 ? "" : separator);
                    }
                }
            }

            return pinyinBuffer.toString().trim();
        } catch (Exception e) {
            throw new RuntimeException("format the word:" + word + " the hanyu-pinyin exception", e);
        }
    }

    private static HanyuPinyinOutputFormat populatePinyinOutputFormat(PinYinChronoUnit chronoUnit) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        if (PinYinChronoUnit.UPPER_CASE.equals(chronoUnit)) {
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        return format;
    }

}
