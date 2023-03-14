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
package com.photowey.easyexcel.in.action.reader;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.photowey.easyexcel.in.action.core.payload.WeixinBankCardImportPayload;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * {@code WeixinBankCardExcelCachedReader}
 *
 * @author photowey
 * @date 2023/03/14
 * @since 1.0.0
 */
@Slf4j
public class WeixinBankCardExcelCachedReader implements ReadListener<WeixinBankCardImportPayload> {

    private final int batchCount;
    private List<WeixinBankCardImportPayload> cached;

    public WeixinBankCardExcelCachedReader() {
        this.batchCount = 100;
        this.clean();
    }

    public WeixinBankCardExcelCachedReader(int batchCount) {
        this.batchCount = batchCount;
        this.clean();
    }

    @Override
    public void invoke(WeixinBankCardImportPayload data, AnalysisContext context) {
        this.cached.add(data);
        if (this.cached.size() >= this.batchCount) {
            this.fire();
            this.clean();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.fire();
        this.clean();
    }

    private void clean() {
        this.cached = ListUtils.newArrayListWithExpectedSize(this.batchCount);
    }

    private void fire() {
        for (WeixinBankCardImportPayload payload : this.cached) {
            log.info("save import model:{}", JSON.toJSONString(payload, JSONWriter.Feature.PrettyFormat));
        }
    }
}
