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
package com.photowey.commom.in.action.converter.currency;

import com.photowey.commom.in.action.number.BigDecimalUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Supplier;

/**
 * {@code CurrencyConverter}
 *
 * @author photowey
 * @date 2023/06/17
 * @since 1.0.0
 */
public interface CurrencyConverter {

    static BigDecimal toFen(BigDecimal cnyYuan) {
        if (cnyYuan == null) {
            return BigDecimal.ZERO;
        }

        return cnyYuan.movePointRight(2).setScale(0, RoundingMode.DOWN);
    }

    static Long toIntFen(BigDecimal cnyYuan) {
        if (cnyYuan == null) {
            return 0L;
        }

        return toFen(toZero(cnyYuan)).longValue();
    }

    static BigDecimal toYuan(BigDecimal cnyFen) {
        if (cnyFen == null) {
            return BigDecimal.ZERO;
        }

        return toZero(cnyFen).movePointLeft(2).setScale(2, RoundingMode.HALF_UP);
    }

    static BigDecimal toYuan(Long cnyIntFen) {
        if (cnyIntFen == null) {
            return BigDecimal.ZERO;
        }

        return toYuan(toZero(cnyIntFen));
    }

    static BigDecimal toZero(Number amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }

        if (amount instanceof BigDecimal) {
            return (BigDecimal) amount;
        }

        return new BigDecimal(amount.toString());
    }

    static BigDecimal toNumber(String amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(amount);
    }

    static String print(BigDecimal amount) {
        if (amount == null) {
            return "0";
        }

        return BigDecimalUtils.toStr(amount);
    }

    static String print(BigDecimal amount, Supplier<String> fx) {
        if (amount == null) {
            return fx.get();
        }

        return BigDecimalUtils.toStr(amount);
    }
}
