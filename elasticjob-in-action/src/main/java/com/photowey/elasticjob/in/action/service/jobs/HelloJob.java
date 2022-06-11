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
package com.photowey.elasticjob.in.action.service.jobs;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Service;

/**
 * {@code HelloJob}
 *
 * @author photowey
 * @date 2022/06/05
 * @since 1.0.0
 */
@Slf4j
@Service
public class HelloJob implements SimpleJob {

    @Override
    public void execute(ShardingContext context) {
        log.info("{} start : {}", HelloJob.class.getName(), System.currentTimeMillis());
    }

}