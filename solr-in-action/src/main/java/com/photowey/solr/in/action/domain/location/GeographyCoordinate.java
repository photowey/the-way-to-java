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
package com.photowey.solr.in.action.domain.location;

/**
 * {@code GeographyCoordinate}
 *
 * @author photowey
 * @date 2022/03/21
 * @since 1.0.0
 */
public class GeographyCoordinate {

    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     *
     */
    private String radius;


    public GeographyCoordinate() {
    }

    public GeographyCoordinate(String longitude, String latitude, String radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getRadius() {
        return radius;
    }

    public String longitude() {
        return longitude;
    }

    public String latitude() {
        return latitude;
    }

    public String radius() {
        return radius;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public GeographyCoordinate longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public GeographyCoordinate latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public GeographyCoordinate radius(String radius) {
        this.radius = radius;
        return this;
    }
}
