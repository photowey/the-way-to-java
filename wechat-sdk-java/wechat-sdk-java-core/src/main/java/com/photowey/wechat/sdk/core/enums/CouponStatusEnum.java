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
package com.photowey.wechat.sdk.core.enums;

/**
 * {@code CouponStatusEnum}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
public enum CouponStatusEnum {

    /**
     * 优惠券状态
     * <p>
     * 代金券状态：
     * SENDED：可用
     * USED：已实扣
     * EXPIRED：已过期
     * 示例值：EXPIRED
     */
    SENDED,
    USED,
    EXPIRED
}
