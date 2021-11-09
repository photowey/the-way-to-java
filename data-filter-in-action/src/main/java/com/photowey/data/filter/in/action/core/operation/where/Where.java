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
package com.photowey.data.filter.in.action.core.operation.where;

import com.photowey.data.filter.in.action.core.condition.ICondition;
import com.photowey.data.filter.in.action.core.context.ConditionContext;
import com.photowey.data.filter.in.action.core.operation.filter.Filter;
import com.photowey.data.filter.in.action.pair.KvPari;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.ServiceLoader;

/**
 * {@code Where}
 *
 * @author photowey
 * @date 2021/11/09
 * @since 1.0.0
 */
@AllArgsConstructor
public class Where {

    private Condition condition;

    public boolean where(Object target) {
        boolean match = true;
        List<Filter> filters = condition.getFilters();
        for (Filter operation : filters) {
            match = this.handlerCondition(operation, match, target);
        }

        return match;
    }

    private boolean handlerCondition(Filter filter, boolean match, Object target) {
        ServiceLoader<ICondition> load = ServiceLoader.load(ICondition.class);
        for (ICondition candidate : load) {
            if (candidate.supports(filter.operation())) {
                ConditionContext<String, Object> context = new ConditionContext<>();
                KvPari<String, Object> kvPair = new KvPari<>();
                kvPair.setKey(filter.field());
                kvPair.setValue(filter.targetValue());
                context.type(filter.operation()).kvPair(kvPair).target(target).match(match);
                return candidate.handle(context);
            }
        }
        return match;
    }

    @Getter
    @AllArgsConstructor
    public static class Condition {
        private List<Filter> filters;
    }
}
