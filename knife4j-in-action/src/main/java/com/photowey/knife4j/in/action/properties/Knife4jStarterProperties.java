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
package com.photowey.knife4j.in.action.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code Knife4jStarterProperties}
 *
 * @author photowey
 * @date 2022/01/14
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "api.knife4j.starter")
public class Knife4jStarterProperties implements Serializable {

    private static final long serialVersionUID = -7992540227934302975L;

    private Project project = new Project();
    private Api api = new Api();

    @Data
    @Validated
    public static class Project implements Serializable {

        private static final long serialVersionUID = -1249689015297696151L;

        private String name;
        @NotBlank(message = "请配置: `knife4j` 文档作者: `author`")
        private String author;
        @NotBlank(message = "请配置: `knife4j` 文档作者邮箱: `email`")
        private String email;
    }

    @Data
    @Validated
    public static class Api implements Serializable {

        private static final long serialVersionUID = 3534129045736770485L;

        private String group;
        private String title;
        private String description;
        private String termsOfServiceUrl = "https://wiki.photowey.dev/photowey";
        private String version = "1.0.0";
        private boolean enableSecurity = false;
        @NotEmpty(message = "请配置: `knife4j` 文档控制器列表: `controllers`")
        private List<String> controllers = new ArrayList<>();
        private List<String> pathRegexes = new ArrayList<>();
    }
}
