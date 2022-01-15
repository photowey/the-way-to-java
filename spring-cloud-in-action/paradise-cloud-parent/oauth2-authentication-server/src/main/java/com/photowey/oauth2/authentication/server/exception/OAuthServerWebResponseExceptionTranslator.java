package com.photowey.oauth2.authentication.server.exception;

import com.photowey.oauth2.authentication.core.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * {@code OAuthServerWebResponseExceptionTranslator}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public class OAuthServerWebResponseExceptionTranslator implements WebResponseExceptionTranslator/*<ResponseModel>*/ {

    @Override
    public ResponseEntity<ResponseModel> translate(Exception e) {
        ResponseModel responseModel = this.doExceptionTranslate(e);
        return new ResponseEntity<>(responseModel, HttpStatus.UNAUTHORIZED);
    }

    private ResponseModel doExceptionTranslate(Exception e) {
        if (e instanceof UnsupportedGrantTypeException) {
            return new ResponseModel(401, "4001", "客户端认证失败", null);
        } else if (e instanceof InvalidGrantException) {
            return new ResponseModel(400, "4000", "用户名或密码错误", null);
        }

        return new ResponseModel(500, "5000", "系统错误", null);
    }
}
