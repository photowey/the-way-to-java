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
package com.photowey.oauth2.authentication.server.handler;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.jwt.model.ResponseModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {@code ResponseHandler}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Component
public class OAuthServerResponseHandler {

    public void populateContentTypeUTF8(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    public void walk(HttpServletResponse response, ResponseModel responseModel) throws IOException {
        this.populateContentTypeUTF8(response);
        ServletOutputStream out = response.getOutputStream();
        out.write(JSON.toJSONString(responseModel).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

}
