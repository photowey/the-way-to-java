/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.commom.in.action.number;

import com.photowey.commom.in.action.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@code BigDecimalUtils}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public final class BigDecimalUtils {

    private BigDecimalUtils() {
        // utility class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    // ---------------------------------------------------------- newBigDecimal

    /**
     * 构造 BigDecimal
     *
     * @param target 长整型数值
     * @return {@link  BigDecimal}
     */
    public static BigDecimal newBigDecimal(Long target) {
        if (null == target) {
            return null;
        }

        return newBigDecimal(String.valueOf(target));
    }

    /**
     * 构造 BigDecimal
     *
     * @param target 整型数值
     * @return {@link  BigDecimal}
     */
    public static BigDecimal newBigDecimal(Integer target) {
        if (null == target) {
            return null;
        }

        return newBigDecimal(String.valueOf(target));
    }

    /**
     * 构造 BigDecimal
     *
     * @param target 字符串型数值
     * @return {@link  BigDecimal}
     */
    public static BigDecimal newBigDecimal(String target) {
        if (null == target) {
            return null;
        }

        return new BigDecimal(target);
    }

    // ---------------------------------------------------------- toBigDecimal

    /**
     * 格式化 BigDecimal
     *
     * @param target        BigDecimal 数值
     * @param decimalPoints 格式
     * @param roundingMode  取整模式
     * @return 格式化 BigDecimal
     */
    public static BigDecimal toBigDecimal(BigDecimal target, String decimalPoints, RoundingMode roundingMode) {
        if (null == target) {
            return null;
        }

        return new BigDecimal(toStr(target, decimalPoints, roundingMode));
    }

    /**
     * 格式化 BigDecimal
     *
     * @param target BigDecimal 数值
     * @return 格式化 BigDecimal 默认:  #0.00 && HALF_UP
     */
    public static BigDecimal toBigDecimal(BigDecimal target) {
        if (null == target) {
            return null;
        }

        return toBigDecimal(target, NumberConstants.TWO_DECIMAL_POINTS, RoundingMode.HALF_UP);
    }

    // ---------------------------------------------------------- divide

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return divide(dividend, divisor, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale, RoundingMode roundingMod) {
        if (ObjectUtils.isNullOrEmpty(dividend)) {
            nullPointerException(dividend, "the dividend can't be null");
        }
        if (ObjectUtils.isNullOrEmpty(divisor)) {
            nullPointerException(divisor, "the divisor can't be null");
        }

        return dividend.divide(divisor, scale, roundingMod);
    }

    public static BigDecimal divide(String dividend, String divisor) {
        return divide(dividend, divisor, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(String dividend, String divisor, int scale, RoundingMode roundingMod) {
        return divide(newBigDecimal(dividend), newBigDecimal(divisor), scale, roundingMod);
    }

    public static BigDecimal divide(Integer dividend, Integer divisor) {
        return divide(dividend, divisor, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(Integer dividend, Integer divisor, int scale, RoundingMode roundingMod) {
        return divide(newBigDecimal(dividend), newBigDecimal(divisor), scale, roundingMod);
    }

    public static BigDecimal divide(Long dividend, Long divisor) {
        return divide(dividend, divisor, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(Long dividend, Long divisor, int scale, RoundingMode roundingMod) {
        return divide(newBigDecimal(dividend), newBigDecimal(divisor), scale, roundingMod);
    }

    // ---------------------------------------------------------- toString

    /**
     * 根据指定的格式和取整Mode 格式化 {@link BigDecimal}
     *
     * @param target        被格式化的数据
     * @param decimalPoints 格式
     * @param roundingMode  取整方式 {@link RoundingMode}
     * @return 格式化字符串金额
     */
    public static String toStr(BigDecimal target, String decimalPoints, RoundingMode roundingMode) {
        if (ObjectUtils.isNullOrEmpty(target)) {
            return "";
        }

        return NumberFormatUtils.format(target, decimalPoints, roundingMode);
    }

    /**
     * 转换为字符串
     *
     * @param target
     * @return
     */
    public static String toPlainString(BigDecimal target) {
        if (ObjectUtils.isNullOrEmpty(target)) {
            return "";
        }

        return target.toPlainString();
    }

    /**
     * 格式化 {@link BigDecimal} 为小数点后两位小数
     *
     * @param target 被格式化的数据
     * @return 格式化字符串金额
     */
    public static String toStr(BigDecimal target) {
        // #0.00
        return toStr(target, NumberConstants.TWO_DECIMAL_POINTS, RoundingMode.HALF_UP);
    }

    // ---------------------------------------------------------- format

    /**
     * 格式化数字为千分符
     * 保留两位小数
     *
     * @param target 被格式化的数据
     * @return 格式化字符串金额
     */
    public static String toThousand(BigDecimal target) {
        if (ObjectUtils.isNullOrEmpty(target)) {
            return "";
        }

        return toStr(target, NumberConstants.PERCENT_WITH_COMMA_2_POINT, RoundingMode.HALF_UP);
    }

    /**
     * 转换为万元
     *
     * @param target 待转化的数值
     * @return 万元数值
     */
    public static BigDecimal toTenThousand(BigDecimal target) {
        if (ObjectUtils.isNullOrEmpty(target) || ObjectUtils.compareEquals(BigDecimal.ZERO, target)) {
            return target;
        }

        return target.divide(newBigDecimal(NumberConstants.TEN_THOUSAND), 2, RoundingMode.HALF_UP);
    }

    /**
     * 比率格式化
     *
     * @param divisor  除数
     * @param dividend 被除数
     * @return 格式化比率
     */
    public static BigDecimal formatRatio(BigDecimal divisor, BigDecimal dividend) {
        if (ObjectUtils.isNullOrEmpty(divisor)) {
            return newBigDecimal("0.0000");
        }
        if (ObjectUtils.isNullOrEmpty(dividend) || ObjectUtils.compareEquals(BigDecimal.ZERO, divisor)) {
            dividend = newBigDecimal("1");
        }
        BigDecimal winRatio = divisor.divide(dividend, 4, RoundingMode.HALF_UP);

        return winRatio;
    }

    // ---------------------------------------------------------- CNY transfer

    /**
     * 圆转分
     *
     * @param yuan 圆
     * @return 分
     */
    public static BigDecimal toCent(BigDecimal yuan) {
        if (yuan == null) {
            return null;
        }

        return toBigDecimal(yuan.multiply(new BigDecimal("100")));
    }

    /**
     * 分转圆
     *
     * @param cent 分
     * @return 圆
     */
    public static BigDecimal toYuan(BigDecimal cent) {
        if (cent == null) {
            return null;
        }

        return divide(cent, new BigDecimal("100"));
    }

    // ---------------------------------------------------------- exception

    public static <T> void nullPointerException(T candidate, String message) {
        if (ObjectUtils.isNullOrEmpty(candidate)) {
            throw new NullPointerException(message);
        }
    }
}