/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.data.filter.in.action.core.enums;

/**
 * {@code AggregationType}
 *
 * @author photowey
 * @date 2021/11/09
 * @since 1.0.0
 */
public enum AggregationType {

    COUNT("*", "COUNT"),
    SUM("*", "SUM"),
    AVG("*", "AVG");

    private String field;
    private String type;

    AggregationType(String field, String type) {
        this.field = field;
        this.type = type;
    }

    public String field() {
        return field;
    }

    public void field(String field) {
        this.field = field;
    }

    public String type() {
        return type;
    }

    public String wrapper() {
        return type + "(" + field + ")";
    }

    public void type(String type) {
        this.type = type;
    }

}
