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
package com.photowey.elasticseatch.in.action.core;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * {@code AbstractSearchQuery}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public abstract class AbstractSearchQuery implements IPagination, Serializable {

    /**
     * 当前页码
     */
    @NotNull(message = "当前页码-不能为空")
    @Min(value = 1, message = "当前页码-最小值为 1")
    @ApiModelProperty(value = "当前页码", required = false, example = "1")
    protected int current = DEFAULT_PAGE_NO;

    /**
     * 每页展示条数(偏移量)
     */
    @NotNull(message = "每页展示条数(偏移量)-不能为空")
    @Min(value = 1, message = "每页展示条数(偏移量)-最小值为 1")
    @Max(value = 100, message = "每页展示条数(偏移量)-最大值为 100")
    @ApiModelProperty(value = "每页展示条数(偏移量)", required = false, example = "10")
    protected int pageSize = DEFAULT_PAGE_SIZE;

    @Override
    public int getCurrent() {
        // 排除绕过框架验证 - 手动设置 非法的值
        return this.current < PAGE_NO_THRESHOLD ? PAGE_NO_THRESHOLD : this.current;
    }

    @Override
    public int getPageSize() {
        return this.pageSize > PAGE_SIZE_THRESHOLD ? PAGE_SIZE_THRESHOLD : this.pageSize;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
