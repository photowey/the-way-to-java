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
package com.photowey.solr.in.action.domain;

import java.io.Serializable;
import java.util.List;

/**
 * {@code PageDocument}
 *
 * @author photowey
 * @date 2022/03/21
 * @since 1.0.0
 */
public class PageDocument<DOCUMENT> implements Serializable {

    private static final long serialVersionUID = 1850177686287274627L;

    private long totalElements;
    private int totalPages;
    private Float maxScore;
    private List<DOCUMENT> documents;

    public PageDocument() {
    }

    public PageDocument(long totalElements, int totalPages, Float maxScore, List<DOCUMENT> documents) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.maxScore = maxScore;
        this.documents = documents;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public Float getMaxScore() {
        return maxScore;
    }

    public List<DOCUMENT> getDocuments() {
        return documents;
    }

    public long totalElements() {
        return totalElements;
    }

    public int totalPages() {
        return totalPages;
    }

    public Float maxScore() {
        return maxScore;
    }

    public List<DOCUMENT> documents() {
        return documents;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setMaxScore(Float maxScore) {
        this.maxScore = maxScore;
    }

    public void setDocuments(List<DOCUMENT> documents) {
        this.documents = documents;
    }

    public PageDocument totalElements(long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public PageDocument totalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PageDocument maxScore(Float maxScore) {
        this.maxScore = maxScore;
        return this;
    }

    public PageDocument documents(List<DOCUMENT> documents) {
        this.documents = documents;
        return this;
    }
}
