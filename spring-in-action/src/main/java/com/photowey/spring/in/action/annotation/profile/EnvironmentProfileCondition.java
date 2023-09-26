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
package com.photowey.spring.in.action.annotation.profile;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code EnvironmentProfileCondition}
 *
 * @author photowey
 * @date 2023/09/26
 * @since 1.0.0
 */
class EnvironmentProfileCondition implements Condition {

    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_VALUE_ATTR_KEY = "value";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> kvs = metadata.getAllAnnotationAttributes(EnvironmentProfile.class.getName());
        if (kvs != null) {
            for (Object profiles : kvs.get(DEFAULT_VALUE_ATTR_KEY)) {
                String[] parsedProfiles = this.parseDynamicProfilesIfNecessary(context, profiles);
                if (context.getEnvironment().acceptsProfiles(Profiles.of(parsedProfiles))) {
                    return true;
                }
            }

            return false;
        }

        return true;
    }

    private String[] parseDynamicProfilesIfNecessary(ConditionContext context, Object profiles) {
        String[] profileArray = (String[]) profiles;
        Environment environment = context.getEnvironment();

        List<String> parsedProfiles = new ArrayList<>();

        for (String profile : profileArray) {
            List<String> parsed = Stream.of(environment.resolvePlaceholders(profile).split(DEFAULT_DELIMITER)).collect(Collectors.toList());
            parsedProfiles.addAll(parsed);
        }

        return parsedProfiles.toArray(String[]::new);
    }
}
