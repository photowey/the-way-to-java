/*
 * Copyright (C) 2021-2023 Thomas Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.common.in.action.shared.json;

import com.photowey.common.in.action.thrower.AssertionErrorThrower;

/**
 * {@code Json}
 * <p>
 * Use {@link com.photowey.common.in.action.shared.json.jackson.JSON} instead.
 *
 * @author photowey
 * @date 2023/10/26
 * @since 1.0.0
 */
@Deprecated
public final class Json {

    private Json() {
        AssertionErrorThrower.throwz(Json.class);
    }

}
