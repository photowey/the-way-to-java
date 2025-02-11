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
package com.photowey.drools.in.action.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code DroolsProperties}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroolsProperties implements Serializable {

    private static final long serialVersionUID = 4479253614243628525L;

    private static final String SLUSH = "/";

    private String rulePath = "io/github/photowey/drools/rules/";

    // ----------------------------------------------------------------

    public String populateClasspathRuleResources() {
        return String.format("classpath*:%s/**/*.*", tryTrim(this.rulePath));
    }

    public String populateRuleFullPath(String filename) {
        return tryTrim(this.rulePath) + "/" + filename;
    }

    // ----------------------------------------------------------------

    public static String tryTrim(String path) {
        String tmp = path;
        if (tmp.startsWith(SLUSH)) {
            tmp = tmp.substring(1);
        }

        if (tmp.endsWith(SLUSH)) {
            tmp = tmp.substring(0, tmp.length() - 1);
        }

        return tmp;
    }

    // ----------------------------------------------------------------

    public static String getPrefix() {
        return "io.github.photowey.drools.rules";
    }
}
