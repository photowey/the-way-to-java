/*
 *  Copyright 1999-2021 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.photowey.spring.cloud.alibaba.seata.account.in.action.controller;

import com.photowey.spring.cloud.alibaba.seata.account.in.action.service.AccountService;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/reduce")
    public Boolean reduce(@RequestParam("userId") String userId, @RequestParam("money") BigDecimal money) {
        System.out.println("account XID " + RootContext.getXID());
        accountService.debit(userId, money);

        return Boolean.TRUE;
    }

}
