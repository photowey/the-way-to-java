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
package com.photowey.common.in.action.formatter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * {@code NumberFormatter}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/12/16
 */
public interface NumberFormatter {

    static String format(Number no, String pattern, RoundingMode rm) {
        if (null == no) {
            throw new NullPointerException("no can't be null");
        }

        if (null == pattern || pattern.isEmpty()) {
            throw new NullPointerException("pattern can't be null");
        }

        DecimalFormat formatter = new DecimalFormat(pattern);
        if (Objects.isNull(rm)) {
            rm = RoundingMode.HALF_UP;
        }

        formatter.setRoundingMode(rm);

        return formatter.format(no);
    }
}

