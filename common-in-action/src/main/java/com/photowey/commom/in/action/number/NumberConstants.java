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

/**
 * {@code NumberConstants}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public interface NumberConstants {

    String NO_SCALE = "#";
    String TWO_DECIMAL_POINTS = "#0.00";
    String FOUR_DECIMAL_POINTS = "#0.0000";

    String PERCENT_WITH_NO_POINT = "##%";
    String PERCENT_WITH_1_POINT = "#0.0%";
    String PERCENT_WITH_2_POINT = "#0.00%";
    String PERCENT_WITH_3_POINT = "#0.000%";

    String PERCENT_WITH_COMMA_2_POINT = "#,##0.00";
    String PERCENT_WITH_COMMA_4_POINT = "#,##0.0000";

    // -------------------------------------------------------------------------

    int ONE = 1;

    int TEN = 10;

    int HUNDRED = 100;

    int THOUSAND = 1000;

    int TEN_THOUSAND = 10000;

    int HUNDRED_THOUSAND = 10 * TEN_THOUSAND;

    int MILLION = 10 * HUNDRED_THOUSAND;

    int TEN_MILLION = 10 * MILLION;

    int HUNDRED_MILLION = 10 * TEN_MILLION;

    int BILLION = 10 * HUNDRED_MILLION;

    long TEN_BILLION = (long) (BILLION) * 10;

    int DEFAULT_DIV_SCALE = 10;
}