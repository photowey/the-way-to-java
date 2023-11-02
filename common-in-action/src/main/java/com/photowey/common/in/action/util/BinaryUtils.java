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
package com.photowey.common.in.action.util;

import com.photowey.common.in.action.thrower.AssertionErrorThrower;

import java.math.BigDecimal;

/**
 * {@code BinaryUtils}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public final class BinaryUtils {

    private static final String POINT = ".";
    private static final char ZERO = '0';
    private static final char ONE = '1';
    private static final char POINT_CHAR = '.';

    private BinaryUtils() {
        // utility class; can't create
        AssertionErrorThrower.throwz(BinaryUtils.class);
    }

    public static String toDecimalString(String binary) {
        if (BinaryUtils.isNotBinary(binary)) {
            throw new IllegalArgumentException(StringFormatUtils.format("Illegal binary: {}!", binary));
        }

        if (BinaryUtils.isContainsPoint(binary)) {
            int pointIndex = binary.indexOf(POINT);
            String integer = binary.substring(0, pointIndex);
            String decimals = binary.substring(pointIndex + 1);
            int integerSum = BinaryUtils.binaryIntToDecimal(integer);
            double decimalsSum = BinaryUtils.binaryDecimalToDecimal(decimals);
            return String.valueOf(integerSum + decimalsSum);
        } else {
            int integer = BinaryUtils.binaryIntToDecimal(binary);
            return String.valueOf(integer);
        }
    }

    public static Integer toInt(String binary) {
        if (BinaryUtils.isNotBinary(binary)) {
            throw new IllegalArgumentException(StringFormatUtils.format("Illegal binary: {}!", binary));
        }

        if (BinaryUtils.isContainsPoint(binary)) {
            throw new IllegalArgumentException(StringFormatUtils.format("Illegal binary: {}!", binary));
        }

        return Integer.parseInt(toDecimalString(binary));
    }

    public static BigDecimal toBigDecimal(String binary) {
        if (BinaryUtils.isNotBinary(binary)) {
            throw new IllegalArgumentException(StringFormatUtils.format("Illegal binary: {}!", binary));
        }

        if (BinaryUtils.isNotContainsPoint(binary)) {
            throw new IllegalArgumentException(StringFormatUtils.format("Illegal binary: {}!", binary));
        }

        return new BigDecimal(toDecimalString(binary));
    }

    private static int binaryIntToDecimal(String binInt) {
        int sum = 0;
        for (int i = binInt.length(); i > 0; i--) {
            int scale = 2;
            if (binInt.charAt(-(i - binInt.length())) == ONE) {
                if (i != 1) {
                    for (int j = 1; j < i - 1; j++) {
                        scale *= 2;
                    }
                } else {
                    scale = 1;
                }
            } else {
                scale = 0;
            }
            sum += scale;
        }

        return sum;
    }

    private static double binaryDecimalToDecimal(String decimals) {
        double decimalsSum = 0F;
        for (int i = 0; i < decimals.length(); i++) {
            double scale = 2;
            if (decimals.charAt(i) == ONE) {
                if (i != 0) {
                    for (int j = 1; j <= i; j++) {
                        scale *= 2;
                    }
                }
                scale = 1 / scale;
            } else {
                scale = 0;
            }
            decimalsSum += scale;
        }

        return decimalsSum;
    }

    private static boolean isNotBinary(String binary) {
        return !isBinary(binary);
    }

    private static boolean isBinary(String binary) {
        boolean passed = true;
        if (binary.contains(POINT)) {
            if (binary.lastIndexOf(POINT) + 1 == binary.length()) {
                return false;
            } else if (binary.indexOf(POINT) == 0) {
                return false;
            }
            char[] chars = binary.toCharArray();
            int sum = 0;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == POINT_CHAR) {
                    sum += 1;
                } else {
                    if (chars[i] != ZERO && chars[i] != ONE) {
                        return false;
                    }
                }
                if (sum > 1) {
                    return false;
                }
            }
        } else {
            char[] chars = binary.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] != ZERO && chars[i] != ONE) {
                    return false;
                }
            }
        }

        return passed;
    }

    private static boolean isNotContainsPoint(String bin) {
        return !isContainsPoint(bin);
    }

    private static boolean isContainsPoint(String bin) {
        return bin.contains(POINT);
    }
}
