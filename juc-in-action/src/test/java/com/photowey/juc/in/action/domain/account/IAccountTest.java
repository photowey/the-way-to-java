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
package com.photowey.juc.in.action.domain.account;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code IAccountTest}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
@Slf4j
class IAccountTest {

    @Test
    void testAccount() throws InterruptedException {

        for (int i = 0; i < 10; i++) {

            // this.doAction(new AccountUnSafe(10_000));
            // this.doAction(new AccountSync(10_000));
            this.doAction(new AccountCas(10_000));
        }

        Thread.sleep(5_000);
    }

    private void doAction(IAccount account) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            threads.add(new Thread(() -> {
                account.acquire(20);
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        log.info("the remain balance is:{}", account.query());

    }

}