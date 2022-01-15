package com.photowey.oauth2.authentication.server.handler;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.core.model.ResponseModel;
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
