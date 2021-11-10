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
package com.photowey.mybatis.in.action.page;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code Page}
 *
 * @param <T> T 类型
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Page<T> extends ArrayList<T> implements Serializable {

    private Integer pageNo;
    private Integer pageSize;
    private Integer offset;
    private Integer rows;
    private Long totalCount;
    private Long totalPages;

    private List<T> data;

    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.calculateOffset();
    }

    private void calculateOffset() {
        this.offset = this.pageNo > 0 ? (this.pageNo - 1) * this.pageSize : 0;
        this.rows = this.offset + this.pageSize * (this.pageNo > 0 ? 1 : 0);
    }

    public void setData(List<T> data) {
        this.data = data;
        this.calculateTotalPages();
    }

    private long calculateTotalPages() {
        if (this.getPageSize() == 0) {
            return 0L;
        }
        long totalPages = this.getTotalCount() / this.getPageSize();
        if (this.getTotalCount() % this.getPageSize() != 0) {
            totalPages++;
        }

        this.totalPages = totalPages;

        return totalPages;
    }
}
