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

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.photowey.easyexcel.in.action.core.payload.WeixinBankCardImportPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code WeixinBankCardExcelReaderTest}
 *
 * @author photowey
 * @date 2023/03/14
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
class WeixinBankCardExcelReaderTest {

    @Autowired
    private ResourceReader resourceReader;

    @Test
    void testImportBankCards() throws IOException {
        // 1:储蓄卡 2:信用卡
        WeixinBankCardExcelReader sheet0 = new WeixinBankCardExcelReader(2);
        WeixinBankCardExcelReader sheet1 = new WeixinBankCardExcelReader(1);

        List<WeixinBankCardImportPayload> cards = new ArrayList<>();

        try (InputStream excel = this.resourceReader.read("/excel/dev/银行卡编码.xlsx");
             ExcelReader excelReader = EasyExcel.read(excel).ignoreEmptyRow(true).build()) {
            ReadSheet readSheet0 =
                    EasyExcel.readSheet(0).head(WeixinBankCardImportPayload.class).registerReadListener(sheet0).build();
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(1).head(WeixinBankCardImportPayload.class).registerReadListener(sheet1).build();
            excelReader.read(readSheet0, readSheet1);

            List<WeixinBankCardImportPayload> read0 = sheet0.read();
            List<WeixinBankCardImportPayload> read1 = sheet1.read();

            cards.addAll(read0);
            cards.addAll(read1);
        }

        for (WeixinBankCardImportPayload payload : cards) {
            log.info("save import model:\n{}", JSON.toJSONString(payload, JSONWriter.Feature.PrettyFormat));
        }
    }

}