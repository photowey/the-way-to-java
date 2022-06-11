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
package com.photowey.solr.in.action.domain.document;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;

/**
 * {@code AreaDocument}
 *
 * @author photowey
 * @date 2022/03/20
 * @since 1.0.0
 */
@Data
@SolrDocument(collection = AreaDocument.SOLR_CORE_AREA_NAME)
public class AreaDocument implements Serializable, SolrSearchableDocument {

    private static final long serialVersionUID = 1752552218886213006L;

    public static final String SOLR_CORE_AREA_NAME = "core-areas";

    @Id
    @Field("id")
    private String id;

    @Field("pid")
    private Integer pid;
    @Field("short_name")
    private String shortName;
    @Field("name")
    private String name;
    @Field("merger_name")
    private String mergerName;
    @Field("level")
    private Integer level;
    @Field("pinyin")
    private String pinyin;
    @Field("code")
    private String code;
    @Field("zip_code")
    private String zipCode;
    @Field("first")
    private String first;
    @Field("longitude")
    private String longitude;
    @Field("latitude")
    private String latitude;

    @Field("geography_location")
    private String geographyLocation;

    @Override
    public String coreName() {
        return SOLR_CORE_AREA_NAME;
    }
}
