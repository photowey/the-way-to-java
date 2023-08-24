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
package com.photowey.easyexcel.in.action.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.photowey.commom.in.action.date.DateUtils;
import com.photowey.easyexcel.in.action.core.export.UserExport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code UserExportController}
 *
 * @author photowey
 * @date 2023/03/19
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserExportController extends AbstractExportController {

    /**
     * @see <a href="http://localhost:7923/v1/user/export"></a></a>
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        this.populateResponseHeader(response, "用户信息");
        List<UserExport> data = populateUserInfos();

        EasyExcel.write(response.getOutputStream(), UserExport.class)
                .sheet("用户")
                .doWrite(data);
    }

    private static List<UserExport> populateUserInfos() {
        List<UserExport> data = new ArrayList<>();
        SecureRandom random = new SecureRandom();
        long now = System.currentTimeMillis() / 1000 * 1000;
        for (int i = 0; i < TOTAL_USERS; i++) {
            UserExport u1 = UserExport.builder()
                    .id(1633368952476377089L + i)
                    .name("姓名:" + i)
                    .age(((i + 1) % 100) + 1)
                    .gender(i % 3)
                    .balance(new BigDecimal(String.valueOf(random.nextInt(10_000_000))))
                    .joinTime(DateUtils.toLocalDateTime(now + 1000 * i))
                    .build();
            data.add(u1);

            log.info("export user, index:{},info:{}", (1 + i), JSON.toJSONString(u1));
        }

        return data;
    }

    private static final int TOTAL_USERS = 1_000_000;
    private static final long BASE_USER_ID = 1633368952476377089L;
}
