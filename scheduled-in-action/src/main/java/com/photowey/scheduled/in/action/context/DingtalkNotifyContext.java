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
package com.photowey.scheduled.in.action.context;

import com.dingtalk.api.request.OapiRobotSendRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code DingtalkNotifyContext}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DingtalkNotifyContext implements NotifyContext {

    private String type;
    private String messageType;
    private String title;
    private String body;
    private Collection<String> targets;

    private OapiRobotSendRequest request;

    @Override
    public void setTargetz(String... mobiles) {
        if (isEmpty(mobiles)) {
            return;
        }

        this.targets = Stream.of(mobiles).collect(Collectors.toSet());
    }

    /**
     * copy from org.apache.commons.lang3.ArrayUtils#isEmpty(java.lang.Object[])
     */
    public static boolean isEmpty(final Object[] array) {
        return getLength(array) == 0;
    }

    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }
}

