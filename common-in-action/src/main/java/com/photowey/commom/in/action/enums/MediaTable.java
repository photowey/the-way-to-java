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
package com.photowey.commom.in.action.enums;

import com.photowey.commom.in.action.util.ObjectUtils;

/**
 * {@code MediaTable}
 *
 * @author photowey
 * @date 2023/05/23
 * @since 1.0.0
 */
public enum MediaTable {

    ;

    public enum Image {

        // 图片

        BMP("bmp", "image/bmp"),
        GIF("gif", "image/gif"),
        JPEG("jpeg", "image/jpg"),
        JPG("jpg", "image/jpg"),
        PNG("png", "image/png"),

        ;

        private final String suffix;
        private final String contentType;

        Image(String suffix, String contentType) {
            this.suffix = suffix;
            this.contentType = contentType;
        }

        public String suffix() {
            return suffix;
        }

        public String contentType() {
            return contentType;
        }

        public static String suffixOf(String suffix) {
            for (Image image : Image.values()) {
                if (image.suffix().equalsIgnoreCase(suffix)) {
                    return image.contentType();
                }
            }

            return null;
        }
    }

    public enum Txt {

        // 文本

        TXT("txt", "text/plain"),
        HTML("html", "text/html"),

        XML("xml", "application/xml"),

        ;

        private final String suffix;
        private final String contentType;

        Txt(String suffix, String contentType) {
            this.suffix = suffix;
            this.contentType = contentType;
        }

        public String suffix() {
            return suffix;
        }

        public String contentType() {
            return contentType;
        }

        public static String suffixOf(String suffix) {
            for (Txt txt : Txt.values()) {
                if (txt.suffix().equalsIgnoreCase(suffix)) {
                    return txt.contentType();
                }
            }

            return null;
        }
    }

    public enum Bin {

        // 二进制

        VSD("vsd", "application/vnd.visio"),

        PPT("ppt", "application/vnd.ms-powerpoint"),
        PPTX("pptx", "application/vnd.ms-powerpoint"),

        DOC("doc", "application/msword"),
        DOCX("docx", "application/msword"),

        XLS("xls", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

        ;

        private final String suffix;
        private final String contentType;

        Bin(String suffix, String contentType) {
            this.suffix = suffix;
            this.contentType = contentType;
        }

        public String suffix() {
            return suffix;
        }

        public String contentType() {
            return contentType;
        }

        public static String suffixOf(String suffix) {
            for (Bin bin : Bin.values()) {
                if (bin.suffix().equalsIgnoreCase(suffix)) {
                    return bin.contentType();
                }
            }

            return null;
        }
    }

    public static String suffixOf(String suffix) {
        String contentType = Image.suffixOf(suffix);
        if (ObjectUtils.isNotNullOrEmpty(contentType)) {
            return contentType;
        }

        contentType = Txt.suffixOf(suffix);
        if (ObjectUtils.isNotNullOrEmpty(contentType)) {
            return contentType;
        }

        contentType = Bin.suffixOf(suffix);
        if (ObjectUtils.isNotNullOrEmpty(contentType)) {
            return contentType;
        }

        return "application/octet.stream";
    }
}
