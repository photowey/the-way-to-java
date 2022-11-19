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
package com.photowey.plugin.xcurl.lang.psi;

import com.intellij.psi.tree.TokenSet;
import com.photowey.plugin.xcurl.lang.XCURLTypes;

/**
 * {@code CURLTokenSets}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public interface CURLTokenSets {

    TokenSet COMMAND = TokenSet.create(XCURLTypes.COMMAND);

    TokenSet METHOD = TokenSet.create(XCURLTypes.METHOD);

    TokenSet KV = TokenSet.create(XCURLTypes.KV);
    TokenSet OPTION = TokenSet.create(XCURLTypes.OPTION);
    TokenSet OPTION_1 = TokenSet.create(XCURLTypes.OPTION_1);
    TokenSet OPTION_1_STATEMENT = TokenSet.create(XCURLTypes.OPTION_1_STATEMENT);
    TokenSet OPTION_2 = TokenSet.create(XCURLTypes.OPTION_2);
    TokenSet OPTION_2_STATEMENT = TokenSet.create(XCURLTypes.OPTION_2_STATEMENT);
    TokenSet OPTION_3 = TokenSet.create(XCURLTypes.OPTION_3);
    TokenSet OPTION_3_STATEMENT = TokenSet.create(XCURLTypes.OPTION_3_STATEMENT);
    TokenSet OPTION_4 = TokenSet.create(XCURLTypes.OPTION_4);
    TokenSet OPTION_4_STATEMENT = TokenSet.create(XCURLTypes.OPTION_4_STATEMENT);
    TokenSet OPTION_5 = TokenSet.create(XCURLTypes.OPTION_5);
    TokenSet OPTION_5_STATEMENT = TokenSet.create(XCURLTypes.OPTION_5_STATEMENT);
    TokenSet OPTION_6 = TokenSet.create(XCURLTypes.OPTION_6);
    TokenSet OPTION_6_STATEMENT = TokenSet.create(XCURLTypes.OPTION_6_STATEMENT);
    TokenSet OPTION_7 = TokenSet.create(XCURLTypes.OPTION_7);
    TokenSet OPTION_7_STATEMENT = TokenSet.create(XCURLTypes.OPTION_7_STATEMENT);
    TokenSet BASIC_STRING = TokenSet.create(XCURLTypes.BASIC_STRING);
    TokenSet COMMENT = TokenSet.create(XCURLTypes.COMMENT);
    TokenSet CURL = TokenSet.create(XCURLTypes.CURL);
    TokenSet QUOTED_STRING = TokenSet.create(XCURLTypes.QUOTED_STRING);
    TokenSet URL = TokenSet.create(XCURLTypes.URL);
}