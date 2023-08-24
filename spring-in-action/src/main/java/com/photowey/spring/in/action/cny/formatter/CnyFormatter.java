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
package com.photowey.spring.in.action.cny.formatter;

import com.photowey.commom.in.action.converter.currency.CurrencyConverter;
import org.springframework.format.Formatter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

/**
 * {@code CnyFormatter}
 *
 * @author photowey
 * @date 2023/06/17
 * @since 1.0.0
 */
public class CnyFormatter implements Formatter<BigDecimal> {

    private boolean toYuan;
    private boolean toFen;

    public CnyFormatter() {
        this(false);
    }

    public CnyFormatter(boolean toYuan) {
        this(toYuan, false);
    }

    public CnyFormatter(boolean toYuan, boolean toFen) {
        this.toYuan = toYuan;
        this.toFen = toFen;
    }

    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
        BigDecimal amount = CurrencyConverter.toNumber(text);
        return toYuan ? CurrencyConverter.toYuan(amount) : CurrencyConverter.toFen(amount);
    }

    @Override
    public String print(BigDecimal object, Locale locale) {
        return CurrencyConverter.print(object, () -> toYuan ? "0.00" : "0");
    }
}
