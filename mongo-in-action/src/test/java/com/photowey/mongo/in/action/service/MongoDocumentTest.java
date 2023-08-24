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
package com.photowey.mongo.in.action.service;

import com.photowey.mongo.in.action.MongoApp;
import com.photowey.mongo.in.action.domain.mongo.AuthorizeNode;
import com.photowey.mongo.in.action.domain.mongo.UserMongoDocument;
import com.photowey.mongo.in.action.operator.MongoOperators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code MongoDocumentTest}
 *
 * @author photowey
 * @date 2022/11/25
 * @since 1.0.0
 */
@SpringBootTest(classes = MongoApp.class)
class MongoDocumentTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testSave() {

        Long userId = 1569406327149649921L;

        List<AuthorizeNode> authorizeNodes = new ArrayList<>();
        AuthorizeNode authorizeNode = new AuthorizeNode();
        authorizeNode.setOrgId(1569406327149640001L);
        authorizeNode.setTenantId(1569406327149640002L);
        authorizeNode.setPermissionId(1569406327149640003L);
        authorizeNode.setPermissionType(1);

        AuthorizeNode authorizeNode2 = new AuthorizeNode();
        authorizeNode2.setOrgId(1569406327149640011L);
        authorizeNode2.setTenantId(1569406327149640012L);
        authorizeNode2.setPermissionId(1569406327149640013L);
        authorizeNode2.setPermissionType(2);

        AuthorizeNode authorizeNode3 = new AuthorizeNode();
        authorizeNode3.setOrgId(1569406327149640011L);
        authorizeNode3.setTenantId(1569406327149640012L);
        authorizeNode3.setPermissionId(1569406327149640014L);
        authorizeNode3.setPermissionType(2);

        authorizeNodes.add(authorizeNode);
        authorizeNodes.add(authorizeNode2);
        authorizeNodes.add(authorizeNode3);

        UserMongoDocument document = new UserMongoDocument();
        document.setUserId(userId);
        document.setAuthorizes(authorizeNodes);

        this.mongoTemplate.save(document);
    }

    @Test
    void testSave_v2() {

        Long userId = 1569406327149649922L;

        List<AuthorizeNode> authorizeNodes = new ArrayList<>();
        AuthorizeNode authorizeNode = new AuthorizeNode();
        authorizeNode.setOrgId(1569406327149640001L);
        authorizeNode.setTenantId(1569406327149640002L);
        authorizeNode.setPermissionId(1569406327149640003L);
        authorizeNode.setPermissionType(1);

        AuthorizeNode authorizeNode2 = new AuthorizeNode();
        authorizeNode2.setOrgId(1569406327149640011L);
        authorizeNode2.setTenantId(1569406327149640012L);
        authorizeNode2.setPermissionId(1569406327149640013L);
        authorizeNode2.setPermissionType(2);

        AuthorizeNode authorizeNode3 = new AuthorizeNode();
        authorizeNode3.setOrgId(1569406327149640011L);
        authorizeNode3.setTenantId(1569406327149640012L);
        authorizeNode3.setPermissionId(1569406327149640014L);
        authorizeNode3.setPermissionType(2);

        authorizeNodes.add(authorizeNode);
        authorizeNodes.add(authorizeNode2);
        authorizeNodes.add(authorizeNode3);

        UserMongoDocument document = new UserMongoDocument();
        document.setUserId(userId);
        document.setAuthorizes(authorizeNodes);

        this.mongoTemplate.save(document);
    }

    @Test
    void testRemoveItemFromList() {
        AuthorizeDeletePayload payload = AuthorizeDeletePayload
                .builder()
                .id("1569406327149649921")
                .orgId(1569406327149640011L)
                .tenantId(1569406327149640012L)
                .permissionId(1569406327149640013L)
                .permissionType(2)
                .build();

        this.removeAuthorize(payload);
    }

    @Test
    void testBatchRemoveItemFromList() {
        AuthorizeDeletePayload payload = AuthorizeDeletePayload
                .builder()
                .id("1569406327149649921")
                .orgId(1569406327149640011L)
                .tenantId(1569406327149640012L)
                .permissionId(1569406327149640013L)
                .permissionIds(Lists.newArrayList(1569406327149640014L))
                .permissionType(2)
                .build();

        this.multiRemoveAuthorize(payload);
    }

    @Test
    void testAddTOSet() {
        AuthorizeDeletePayload payload = AuthorizeDeletePayload
                .builder()
                .id("1569406327149649921")
                .orgId(1569406327149640011L)
                .tenantId(1569406327149640012L)
                .permissionId(1569406327149640013L)
                .permissionType(2)
                .build();

        this.addTOSet(payload);
    }

    @Test
    void testAddTOSet_v2() {
        AuthorizeDeletePayload payload = AuthorizeDeletePayload
                .builder()
                .id("1569406327149649921")
                .orgId(1569406327149640011L)
                .tenantId(1569406327149640012L)
                .permissionId(1569406327149640017L)
                .permissionType(2)
                .build();

        this.addTOSet_v2(payload);
    }

    @Test
    void testAddTOSet_v3() {
        AuthorizeDeletePayload payload = AuthorizeDeletePayload
                .builder()
                .id("1569406327149649921")
                .orgId(1569406327149640011L)
                .tenantId(1569406327149640012L)
                .permissionId(1569406327149640018L)
                .permissionType(2)
                .build();

        this.addTOSet_v3(payload);
    }

    @Test
    void testBatchRemoveItemFromList_v2() {
        AuthorizeDeletePayload payload = AuthorizeDeletePayload
                .builder()
                .orgId(1569406327149640001L)
                .permissionId(1569406327149640003L)
                .build();

        this.removeAuthorize_v2(payload);
    }

    private void removeAuthorize(AuthorizeDeletePayload payload) {
        Query query = MongoOperators.findById(payload.getId());

        AuthorizeNode authorizeNode = new AuthorizeNode();
        authorizeNode.setOrgId(payload.getOrgId());
        authorizeNode.setPermissionId(payload.getPermissionId());

        Update update = new Update();
        update.pull(UserMongoDocument.getAuthorizesKey(), authorizeNode);

        mongoTemplate.updateFirst(query, update, UserMongoDocument.class);
    }

    private void removeAuthorize_v2(AuthorizeDeletePayload payload) {
        Query query = MongoOperators.eq("authorizes.orgId", payload.getOrgId());
        MongoOperators.eq(query, "authorizes.permissionId", payload.getPermissionId());

        AuthorizeNode authorizeNode = new AuthorizeNode();
        authorizeNode.setOrgId(payload.getOrgId());
        authorizeNode.setPermissionId(payload.getPermissionId());

        Update update = new Update();
        update.pull(UserMongoDocument.getAuthorizesKey(), authorizeNode);

        mongoTemplate.updateMulti(query, update, UserMongoDocument.class);
    }

    private void multiRemoveAuthorize(AuthorizeDeletePayload payload) {
        Query query = MongoOperators.findById(payload.getId());

        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, UserMongoDocument.class);
        for (Long determinePermission : payload.determinePermissions()) {
            AuthorizeNode target = new AuthorizeNode();
            target.setOrgId(payload.getOrgId());
            target.setPermissionId(determinePermission);

            Update update = new Update();
            update.pull(UserMongoDocument.getAuthorizesKey(), target);

            bulkOps.updateOne(query, update);
        }

        bulkOps.execute();
    }

    private void addTOSet(AuthorizeDeletePayload payload) {
        Query query = MongoOperators.findById(payload.getId());

        AuthorizeNode authorizeNode = new AuthorizeNode();
        authorizeNode.setOrgId(payload.getOrgId());
        authorizeNode.setTenantId(payload.getTenantId());
        authorizeNode.setPermissionId(payload.getPermissionId());
        authorizeNode.setPermissionType(payload.getPermissionType());

        Update update = new Update();
        update.addToSet(UserMongoDocument.getAuthorizesKey(), authorizeNode);

        mongoTemplate.updateFirst(query, update, UserMongoDocument.class);
    }

    private void addTOSet_v2(AuthorizeDeletePayload payload) {
        Query query = MongoOperators.findById(payload.getId());

        AuthorizeNode authorizeNode = new AuthorizeNode();
        authorizeNode.setOrgId(payload.getOrgId());
        authorizeNode.setTenantId(payload.getTenantId());
        authorizeNode.setPermissionId(payload.getPermissionId());
        authorizeNode.setPermissionType(payload.getPermissionType());

        AuthorizeNode authorizeNode2 = new AuthorizeNode();
        authorizeNode2.setOrgId(authorizeNode.getOrgId() + 2);
        authorizeNode2.setTenantId(authorizeNode.getTenantId() + 2);
        authorizeNode2.setPermissionId(authorizeNode.getPermissionId() + 2);
        authorizeNode2.setPermissionType(authorizeNode.getPermissionType() + 2);

        List<AuthorizeNode> authorizeNodes = new ArrayList<>();
        authorizeNodes.add(authorizeNode);
        authorizeNodes.add(authorizeNode2);

        Update update = new Update();
        update.addToSet(UserMongoDocument.getAuthorizesKey(), authorizeNodes);

        mongoTemplate.updateMulti(query, update, UserMongoDocument.class);
    }

    private void addTOSet_v3(AuthorizeDeletePayload payload) {
        Query query = MongoOperators.findById(payload.getId());

        AuthorizeNode authorizeNode = new AuthorizeNode();
        authorizeNode.setOrgId(payload.getOrgId());
        authorizeNode.setTenantId(payload.getTenantId());
        authorizeNode.setPermissionId(payload.getPermissionId());
        authorizeNode.setPermissionType(payload.getPermissionType());

        AuthorizeNode authorizeNode2 = new AuthorizeNode();
        authorizeNode2.setOrgId(authorizeNode.getOrgId() + 2);
        authorizeNode2.setTenantId(authorizeNode.getTenantId() + 2);
        authorizeNode2.setPermissionId(authorizeNode.getPermissionId() + 2);
        authorizeNode2.setPermissionType(authorizeNode.getPermissionType() + 2);

        List<AuthorizeNode> authorizeNodes = new ArrayList<>();
        authorizeNodes.add(authorizeNode);
        authorizeNodes.add(authorizeNode2);

        for (AuthorizeNode node : authorizeNodes) {
            Update update = new Update();
            update.addToSet(UserMongoDocument.getAuthorizesKey(), node);

            mongoTemplate.updateFirst(query, update, UserMongoDocument.class);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorizeDeletePayload implements Serializable {

        private static final long serialVersionUID = 3231182673627132250L;

        private String id;
        private Long userId;
        private Long tenantId;
        private Long orgId;
        private Long permissionId;
        private List<Long> permissionIds;
        private Integer permissionType;

        public Set<Long> determinePermissions() {
            Set<Long> ps = new HashSet<>();
            if (!CollectionUtils.isEmpty(this.getPermissionIds())) {
                ps.addAll(this.getPermissionIds());
            }
            if (this.getPermissionId() != null) {
                ps.add(this.getPermissionId());
            }

            return ps;
        }
    }
}
