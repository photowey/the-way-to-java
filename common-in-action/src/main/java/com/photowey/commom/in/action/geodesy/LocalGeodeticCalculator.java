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
package com.photowey.commom.in.action.geodesy;

import com.photowey.commom.in.action.number.BigDecimalUtils;
import com.photowey.commom.in.action.number.NumberConstants;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

/**
 * {@code LocalGeodeticCalculator}
 *
 * @author photowey
 * @date 2023/05/26
 * @since 1.0.0
 */
public final class LocalGeodeticCalculator {

    private static final BigDecimal KM = new BigDecimal("1000");

    private static class LocalGeodeticCalculatorHolder {
        private static final LocalGeodeticCalculator INSTANCE = new LocalGeodeticCalculator();
    }

    private static class GeodeticCalculatorHolder {
        private static final GeodeticCalculator INSTANCE = new GeodeticCalculator();
    }

    public static LocalGeodeticCalculator getInstance() {
        return LocalGeodeticCalculatorHolder.INSTANCE;
    }

    public static GeodeticCalculator getGeodeticCalculator() {
        return GeodeticCalculatorHolder.INSTANCE;
    }

    public BigDecimal tryLocalCalculateDistanceKm(Point from, Point to) {
        return this.tryLocalCalculateDistance(from, to, distance -> {
            return this.formatKm(distance / KM.doubleValue());
        });
    }

    public BigDecimal tryLocalCalculateDistanceM(Point from, Point to) {
        return this.tryLocalCalculateDistance(from, to, this::formatM);
    }

    public BigDecimal tryLocalCalculateDistance(Point from, Point to, Function<Double, BigDecimal> fx) {
        GlobalCoordinates source = new GlobalCoordinates(from.getY(), from.getX());
        GlobalCoordinates target = new GlobalCoordinates(to.getY(), to.getX());
        double distance = getGeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();

        return fx.apply(distance);
    }

    public BigDecimal formatKm(double distance) {
        return this.formatKm(distance, NumberConstants.FIVE_DECIMAL_POINTS);
    }

    public BigDecimal formatKm(double distance, String pattern) {
        return this.kmTom(distance, pattern);
    }

    public BigDecimal formatM(double distance) {
        return this.formatDistance(distance, NumberConstants.TWO_DECIMAL_POINTS);
    }

    public BigDecimal kmTom(double distanceKm, String pattern) {
        BigDecimal distanceM = new BigDecimal(String.valueOf(distanceKm)).multiply(KM);
        return this.formatDistance(distanceM, pattern);
    }

    public BigDecimal formatDistance(double distance, String pattern) {
        BigDecimal distanceB = new BigDecimal(String.valueOf(distance));
        return this.formatDistance(distanceB, pattern);
    }

    public BigDecimal formatDistance(BigDecimal distanceB, String pattern) {
        return BigDecimalUtils.toBigDecimal(distanceB, pattern, RoundingMode.HALF_UP);
    }
}
