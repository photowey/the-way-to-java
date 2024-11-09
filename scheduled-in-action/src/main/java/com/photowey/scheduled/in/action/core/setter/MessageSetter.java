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
package com.photowey.scheduled.in.action.core.setter;

import com.photowey.scheduled.in.action.context.NotifyContext;
import org.springframework.core.Ordered;

/**
 * {@code MessageSetter}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
public interface MessageSetter extends Ordered {

    boolean supports(NotifyContext ctx);

    void advance(NotifyContext ctx);

    @Override
    default int getOrder() {
        return 0;
    }
}

