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
package com.photowey.common.in.action.enums;

import com.photowey.common.in.action.util.ObjectUtils;

/**
 * {@code MediaTable}
 * <p>
 * Nginx:
 * <p>
 * - mime.types:
 * <p>
 *
 * <pre>
 * types {
 *     text/html                                        html htm shtml;
 *     text/css                                         css;
 *     text/xml                                         xml;
 *     image/gif                                        gif;
 *     image/jpeg                                       jpeg jpg;
 *     application/javascript                           js;
 *     application/atom+xml                             atom;
 *     application/rss+xml                              rss;
 *
 *     text/mathml                                      mml;
 *     text/plain                                       txt;
 *     text/vnd.sun.j2me.app-descriptor                 jad;
 *     text/vnd.wap.wml                                 wml;
 *     text/x-component                                 htc;
 *
 *     image/avif                                       avif;
 *     image/png                                        png;
 *     image/svg+xml                                    svg svgz;
 *     image/tiff                                       tif tiff;
 *     image/vnd.wap.wbmp                               wbmp;
 *     image/webp                                       webp;
 *     image/x-icon                                     ico;
 *     image/x-jng                                      jng;
 *     image/x-ms-bmp                                   bmp;
 *
 *     font/woff                                        woff;
 *     font/woff2                                       woff2;
 *
 *     application/java-archive                         jar war ear;
 *     application/json                                 json;
 *     application/mac-binhex40                         hqx;
 *     application/msword                               doc;
 *     application/pdf                                  pdf;
 *     application/postscript                           ps eps ai;
 *     application/rtf                                  rtf;
 *     application/vnd.apple.mpegurl                    m3u8;
 *     application/vnd.google-earth.kml+xml             kml;
 *     application/vnd.google-earth.kmz                 kmz;
 *     application/vnd.ms-excel                         xls;
 *     application/vnd.ms-fontobject                    eot;
 *     application/vnd.ms-powerpoint                    ppt;
 *     application/vnd.oasis.opendocument.graphics      odg;
 *     application/vnd.oasis.opendocument.presentation  odp;
 *     application/vnd.oasis.opendocument.spreadsheet   ods;
 *     application/vnd.oasis.opendocument.text          odt;
 *     application/vnd.openxmlformats-officedocument.presentationml.presentation
 *                                                      pptx;
 *     application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
 *                                                      xlsx;
 *     application/vnd.openxmlformats-officedocument.wordprocessingml.document
 *                                                      docx;
 *     application/vnd.wap.wmlc                         wmlc;
 *     application/wasm                                 wasm;
 *     application/x-7z-compressed                      7z;
 *     application/x-cocoa                              cco;
 *     application/x-java-archive-diff                  jardiff;
 *     application/x-java-jnlp-file                     jnlp;
 *     application/x-makeself                           run;
 *     application/x-perl                               pl pm;
 *     application/x-pilot                              prc pdb;
 *     application/x-rar-compressed                     rar;
 *     application/x-redhat-package-manager             rpm;
 *     application/x-sea                                sea;
 *     application/x-shockwave-flash                    swf;
 *     application/x-stuffit                            sit;
 *     application/x-tcl                                tcl tk;
 *     application/x-x509-ca-cert                       der pem crt;
 *     application/x-xpinstall                          xpi;
 *     application/xhtml+xml                            xhtml;
 *     application/xspf+xml                             xspf;
 *     application/zip                                  zip;
 *
 *     application/octet-stream                         bin exe dll;
 *     application/octet-stream                         deb;
 *     application/octet-stream                         dmg;
 *     application/octet-stream                         iso img;
 *     application/octet-stream                         msi msp msm;
 *
 *     audio/midi                                       mid midi kar;
 *     audio/mpeg                                       mp3;
 *     audio/ogg                                        ogg;
 *     audio/x-m4a                                      m4a;
 *     audio/x-realaudio                                ra;
 *
 *     video/3gpp                                       3gpp 3gp;
 *     video/mp2t                                       ts;
 *     video/mp4                                        mp4;
 *     video/mpeg                                       mpeg mpg;
 *     video/quicktime                                  mov;
 *     video/webm                                       webm;
 *     video/x-flv                                      flv;
 *     video/x-m4v                                      m4v;
 *     video/x-mng                                      mng;
 *     video/x-ms-asf                                   asx asf;
 *     video/x-ms-wmv                                   wmv;
 *     video/x-msvideo                                  avi;
 * }
 * </pre>
 *
 * @author photowey
 * @date 2023/05/23
 * @since 1.0.0
 */
public enum MediaTable {

    ;

    public enum Default {

        DEFAULT("*", "application/octet-stream"),

        ;

        private final String suffix;
        private final String contentType;

        Default(String suffix, String contentType) {
            this.suffix = suffix;
            this.contentType = contentType;
        }

        public String suffix() {
            return suffix;
        }

        public String contentType() {
            return contentType;
        }
    }

    public enum Audio {

        MP3("mp3", "audio/mpeg"),
        MIDI("midi", "audio/midi"),

        DEFAULT("*", "application/octet-stream"),

        ;

        private final String suffix;
        private final String contentType;

        Audio(String suffix, String contentType) {
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
            for (Audio target : Audio.values()) {
                if (target.suffix().equalsIgnoreCase(suffix)) {
                    return target.contentType();
                }
            }

            return null;
        }
    }

    public enum Video {

        MP4("mp4", "video/mp4"),
        AVI("avi", "video/x-msvideo"),
        WMV("wmv", "video/x-ms-wmv"),
        FLV("flv", "video/x-flv"),
        MOV("mov", "video/quicktime"),
        MPG("mpg", "video/mpeg"),
        MPEG("mpeg", "video/mpeg"),
        M4V("m4v", "video/x-m4v"),
        ASF("asf", "video/x-ms-asf"),

        DEFAULT("*", "application/octet-stream"),

        ;

        private final String suffix;
        private final String contentType;

        Video(String suffix, String contentType) {
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
            for (Video target : Video.values()) {
                if (target.suffix().equalsIgnoreCase(suffix)) {
                    return target.contentType();
                }
            }

            return null;
        }
    }

    public enum Image {

        BMP("bmp", "image/bmp"),
        GIF("gif", "image/gif"),
        JPEG("jpeg", "image/jpg"),
        JPG("jpg", "image/jpg"),
        PNG("png", "image/png"),

        DEFAULT("*", "application/octet-stream"),

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
            for (Image target : Image.values()) {
                if (target.suffix().equalsIgnoreCase(suffix)) {
                    return target.contentType();
                }
            }

            return null;
        }
    }

    public enum Txt {

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
            for (Txt target : Txt.values()) {
                if (target.suffix().equalsIgnoreCase(suffix)) {
                    return target.contentType();
                }
            }

            return null;
        }
    }

    public enum Payload {

        JSON("json", "application/json; charset=utf-8"),
        FORM("form", "application/x-www-form-urlencoded"),

        ;

        private final String suffix;
        private final String contentType;

        Payload(String suffix, String contentType) {
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
            for (Payload target : Payload.values()) {
                if (target.suffix().equalsIgnoreCase(suffix)) {
                    return target.contentType();
                }
            }

            return null;
        }
    }

    public enum Bin {

        VSD("vsd", "application/vnd.visio"),

        PPT("ppt", "application/vnd.ms-powerpoint"),
        PPTX("pptx", "application/vnd.ms-powerpoint"),

        DOC("doc", "application/msword"),
        DOCX("docx", "application/msword"),

        XLS("xls", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

        DEFAULT("*", "application/octet-stream"),

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
            for (Bin target : Bin.values()) {
                if (target.suffix().equalsIgnoreCase(suffix)) {
                    return target.contentType();
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

        contentType = Video.suffixOf(suffix);
        if (ObjectUtils.isNotNullOrEmpty(contentType)) {
            return contentType;
        }

        contentType = Audio.suffixOf(suffix);
        if (ObjectUtils.isNotNullOrEmpty(contentType)) {
            return contentType;
        }

        contentType = Txt.suffixOf(suffix);
        if (ObjectUtils.isNotNullOrEmpty(contentType)) {
            return contentType;
        }

        contentType = Payload.suffixOf(suffix);
        if (ObjectUtils.isNotNullOrEmpty(contentType)) {
            return contentType;
        }

        contentType = Bin.suffixOf(suffix);
        if (ObjectUtils.isNotNullOrEmpty(contentType)) {
            return contentType;
        }

        return Default.DEFAULT.contentType();
    }
}
