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
package com.photowey.rxtx.in.action.serialport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * {@code SerialPortHandlerTest}
 *
 * @author photowey
 * @date 2022/02/10
 * @since 1.0.0
 */
class SerialPortHandlerTest {

    /**
     * Stable Library
     * =========================================
     * Native lib Version = RXTX-2.2-20081207 Cloudhopper Build rxtx.cloudhopper.net
     * Java lib Version   = RXTX-2.1-7
     * WARNING:  RXTX Version mismatch
     * Jar version = RXTX-2.1-7
     * native lib Version = RXTX-2.2-20081207 Cloudhopper Build rxtx.cloudhopper.net
     */
    @Test
    void testSerialPost() {

        List<String> ports = SerialPortHandler.findPorts();
        Assertions.assertEquals(2, ports.size());
        Assertions.assertTrue(ports.contains("COM1"));
        Assertions.assertTrue(ports.contains("COM2"));

    }

}