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
package com.photowey.spring.boot.property.refresher.core.domain.meta;

import java.io.Serializable;
import java.util.Objects;

/**
 * {@code ConfigMeta}
 *
 * @author photowey
 * @date 2023/06/29
 * @since 1.0.0
 */
public class ConfigMeta implements Serializable {

    private static final long serialVersionUID = 6195638298432332763L;

    private String groupId;
    private String dataId;
    private String type;

    public static ConfigMetaBuilder builder() {
        return new ConfigMetaBuilder();
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getDataId() {
        return this.dataId;
    }

    public String getType() {
        return this.type;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public void setDataId(final String dataId) {
        this.dataId = dataId;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public ConfigMeta() {
    }

    public ConfigMeta(final String groupId, final String dataId, final String type) {
        this.groupId = groupId;
        this.dataId = dataId;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigMeta that = (ConfigMeta) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(dataId, that.dataId) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, dataId, type);
    }

    public static class ConfigMetaBuilder {
        private String groupId;
        private String dataId;
        private String type;

        ConfigMetaBuilder() {
        }

        public ConfigMetaBuilder groupId(final String groupId) {
            this.groupId = groupId;
            return this;
        }

        public ConfigMetaBuilder dataId(final String dataId) {
            this.dataId = dataId;
            return this;
        }

        public ConfigMetaBuilder type(final String type) {
            this.type = type;
            return this;
        }

        public ConfigMeta build() {
            return new ConfigMeta(this.groupId, this.dataId, this.type);
        }

        public String toString() {
            return "ConfigMeta.ConfigMetaBuilder(groupId=" + this.groupId + ", dataId=" + this.dataId + ", type=" + this.type + ")";
        }
    }
}
