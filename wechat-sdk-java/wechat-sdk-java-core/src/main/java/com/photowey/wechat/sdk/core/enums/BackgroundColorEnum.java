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
package com.photowey.wechat.sdk.core.enums;

import com.photowey.wechat.sdk.core.domain.color.BackgroundColor;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code BackgroundColorEnum}
 *
 * @author photowey
 * @date 2023/08/24
 * @since 1.0.0
 */
public enum BackgroundColorEnum {

    /**
     * 背景色
     */
    COLOR010("COLOR010", "R99", "G179", "B89", "#63B359"),
    COLOR020("COLOR020", "R44", "G159", "B103", "#2C9F67"),
    COLOR030("COLOR030", "R80", "G159", "B201", "#509FC9"),
    COLOR040("COLOR040", "R88", "G133", "B207", "#5885CF"),
    COLOR050("COLOR050", "R144", "G98", "B192", "#9062C0"),

    COLOR060("COLOR060", "R208", "G154", "B69", "#D09A45"),
    COLOR070("COLOR070", "R228", "G177", "B56", "#E4B138"),
    COLOR080("COLOR080", "R238", "G144", "B60", "#EE903C"),
    COLOR090("COLOR090", "R221", "G101", "B73", "#DD6549"),
    COLOR100("COLOR100", "R204", "G70", "B61", "#CC463D"),

    ;

    private static final List<BackgroundColor> BACKGROUND_COLORS;

    private final String code;
    private final String red;
    private final String green;
    private final String blue;
    private final String hex;

    static {
        BACKGROUND_COLORS = toBackgroundColors();
    }

    BackgroundColorEnum(String code, String red, String green, String blue, String hex) {
        this.code = code;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.hex = hex;
    }

    public static BackgroundColorEnum codeOf(String code) {
        for (BackgroundColorEnum color : values()) {
            if (color.code().equals(code)) {
                return color;
            }
        }

        return null;
    }

    private static List<BackgroundColor> toBackgroundColors() {
        List<BackgroundColor> colors = new ArrayList<>();
        for (BackgroundColorEnum backgroundColor : values()) {
            BackgroundColor tt = BackgroundColor.builder()
                    .code(backgroundColor.code())
                    .red(backgroundColor.red().substring(1))
                    .green(backgroundColor.green().substring(1))
                    .blue(backgroundColor.blue().substring(1))
                    .hex(backgroundColor.hex())
                    .build();

            colors.add(tt);
        }

        return colors;
    }

    public static List<BackgroundColor> toBackgroundColorList() {
        return BACKGROUND_COLORS;
    }

    public String code() {
        return code;
    }

    public String red() {
        return red;
    }

    public String green() {
        return green;
    }

    public String blue() {
        return blue;
    }

    public String hex() {
        return hex;
    }
}
