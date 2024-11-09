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
package com.photowey.scheduled.in.action.dingtalk.sign;

import com.photowey.scheduled.in.action.core.exception.DingtalkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * {@code DingtalkSignAlgorithmImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Slf4j
@Component
public class DingtalkSignAlgorithmImpl implements DingtalkSignAlgorithm<SignResult> {

    @Override
    public SignResult sign(String secret) {
        try {
            Long now = System.currentTimeMillis();
            String sign = this.algorithm(now, secret);
            return SignResult.builder().timestamp(now).sign(sign).build();
        } catch (Exception e) {
            throw new DingtalkException("钉钉通知签名失败", e);
        }
    }
}
