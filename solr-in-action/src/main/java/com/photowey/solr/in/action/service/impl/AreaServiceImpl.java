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
package com.photowey.solr.in.action.service.impl;

import com.photowey.solr.in.action.domain.PageDocument;
import com.photowey.solr.in.action.domain.document.AreaDocument;
import com.photowey.solr.in.action.domain.location.GeographyCoordinate;
import com.photowey.solr.in.action.service.AreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.core.RequestMethod;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * {@code AreaServiceImpl}
 *
 * @author photowey
 * @date 2022/03/21
 * @since 1.0.0
 */
@Service
public class AreaServiceImpl implements AreaService {

    private static final String DOT = "\\.";
    private static final String EMPTY_STRING = "";

    private final SolrTemplate solrTemplate;

    public AreaServiceImpl(SolrTemplate solrTemplate) {
        this.solrTemplate = solrTemplate;
    }

    @Override
    public PageDocument<AreaDocument> searchNearby(GeographyCoordinate coordinate) {
        Query query = new SimpleQuery("*:*");
        this.populatePage(query);

        FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("geography_location").near(this.toPoint(coordinate), this.toDistance(coordinate)));
        query.addFilterQuery(filterQuery);

        ScoredPage<AreaDocument> documents = this.solrTemplate.queryForPage(
                AreaDocument.SOLR_CORE_AREA_NAME, query, AreaDocument.class, RequestMethod.GET);

        long totalElements = documents.getTotalElements();
        int totalPages = documents.getTotalPages();
        Float maxScore = documents.getMaxScore();

        return new PageDocument<>(totalElements, totalPages, maxScore, documents.getContent());

    }

    @Override
    public PageDocument<AreaDocument> searchNearbyFilter(GeographyCoordinate coordinate) {
        Query query = new SimpleQuery("*:*");
        this.populatePage(query);

        FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("geography_location").near(this.toPoint(coordinate), this.toDistance(coordinate)));
        query.addFilterQuery(filterQuery);
        query.addFilterQuery(new SimpleFilterQuery(new Criteria("level").greaterThan(1)));

        query.addSort(Sort.by(Sort.Direction.DESC, "level"));
        query.addSort(Sort.by(Sort.Direction.ASC, "id"));

        ScoredPage<AreaDocument> documents = this.solrTemplate.queryForPage(
                AreaDocument.SOLR_CORE_AREA_NAME, query, AreaDocument.class, RequestMethod.GET);

        long totalElements = documents.getTotalElements();
        int totalPages = documents.getTotalPages();
        Float maxScore = documents.getMaxScore();

        return new PageDocument<>(totalElements, totalPages, maxScore, documents.getContent());
    }

    private void populatePage(Query query) {
        query.setOffset(0L);
        query.setRows(5);
    }

    private Distance toDistance(GeographyCoordinate coordinate) {
        return new Distance(this.toDouble(coordinate.radius()));
    }

    private Point toPoint(GeographyCoordinate coordinate) {
        return new Point(this.toDouble(coordinate.latitude()), this.toDouble(coordinate.longitude()));
    }

    public Double toDouble(String number) {
        if (StringUtils.isNumeric(number.replaceAll(DOT, EMPTY_STRING))) {
            return new BigDecimal(number).doubleValue();
        }

        throw new IllegalArgumentException("input number, please.");
    }
}
