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
package com.photowey.commom.in.action.date;

/**
 * {@code DatePatternConstants}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public interface DatePatternConstants {

    String yyyy_MM_dd = "yyyy-MM-dd ";
    String HH_mm_ss = "HH:mm:ss";
    String yyyyMMdd = "yyyyMMdd";
    String yyMMdd = "yyMMdd";
    String HHmmss = "HHmmss";

    String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

    /**
     * {@code ES} default data pattern
     */
    String yyyy_MM_dd_T_HH_mm_ss_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    char RFC_3339_T = 'T';
    String RFC_3339_T_STRING = "'T'";
    String GMT_8 = "+08:00";

    /**
     * RFC 3339 date pattern.
     */
    String RFC_3339_GMT_8 = rfc3339();

    static String rfc3339() {
        return rfc3339Zone(GMT_8);
    }

    static String rfc3339Zone(String zone) {
        StringBuilder builder = new StringBuilder();
        builder.append(yyyy_MM_dd)
                .append(RFC_3339_T_STRING)
                .append(HH_mm_ss)
                .append(zone);

        return builder.toString();
    }

}
