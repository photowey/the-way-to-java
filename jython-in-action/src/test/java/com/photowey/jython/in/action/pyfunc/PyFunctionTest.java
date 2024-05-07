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
package com.photowey.jython.in.action.pyfunc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * {@code PyFunctionTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/07
 */
class PyFunctionTest {

    @Test
    void testFunction() throws IOException {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            Resource pyfile = new ClassPathResource("dev/script/python/add.py");
            interpreter.execfile(pyfile.getInputStream());
            PyFunction pyfunc = interpreter.get("add", PyFunction.class);
            int a = 1024, b = 8848;
            PyObject pyobj = pyfunc.__call__(new PyInteger(a), new PyInteger(b));
            Assertions.assertEquals((a + b), pyobj.__tojava__(Integer.class));
        }
    }
}