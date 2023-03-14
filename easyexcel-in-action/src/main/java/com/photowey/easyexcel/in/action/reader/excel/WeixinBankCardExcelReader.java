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
package com.photowey.easyexcel.in.action.reader.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.photowey.easyexcel.in.action.core.payload.WeixinBankCardImportPayload;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code WeixinBankCardExcelReader}
 *
 * @author photowey
 * @date 2023/03/14
 * @since 1.0.0
 */
@Slf4j
public class WeixinBankCardExcelReader implements ReadListener<WeixinBankCardImportPayload> {

    // TODO WARNING: 确保数据量不大的情况下使用

    private final int cardType;
    private List<WeixinBankCardImportPayload> data = new ArrayList<>();

    public WeixinBankCardExcelReader(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public void invoke(WeixinBankCardImportPayload data, AnalysisContext context) {
        data.setCardType(this.cardType);
        this.data.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.fire();
    }

    public void clean() {
        this.data.clear();
    }

    public List<WeixinBankCardImportPayload> read() {
        return data;
    }

    private void fire() {

    }
}
