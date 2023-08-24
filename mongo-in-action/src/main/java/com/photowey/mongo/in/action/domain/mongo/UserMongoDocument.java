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
package com.photowey.mongo.in.action.domain.mongo;

import com.photowey.mongo.in.action.annotation.BusinessId;
import com.photowey.mongo.in.action.annotation.DocumentId;
import com.photowey.mongo.in.action.document.MongoDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code UserMongoDocument}
 *
 * @author photowey
 * @date 2022/11/25
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "user-hub")
public class UserMongoDocument implements Serializable, MongoDocument {

    private static final long serialVersionUID = -3798281211137251168L;

    @Id
    @DocumentId
    private String id;

    @BusinessId
    private Long userId;

    private List<AuthorizeNode> authorizes = new ArrayList<>();

    @Override
    public String getBusinessId() {
        return String.valueOf(this.userId);
    }

    public static String getAuthorizesKey() {
        return "authorizes";
    }
}
