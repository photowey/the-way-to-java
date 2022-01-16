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
package com.photowey.oauth2.authentication.server.controler;

import com.photowey.oauth2.authentication.core.constant.TokenConstants;
import com.photowey.oauth2.authentication.core.model.AuthUser;
import com.photowey.oauth2.authentication.core.model.ResponseModel;
import com.photowey.oauth2.authentication.core.util.OAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * {@code LogoutController}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class LogoutController {

    private final StringRedisTemplate stringRedisTemplate;

    public LogoutController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseModel> logout() {
        AuthUser authUser = OAuthUtils.authUser();
        this.toCache(authUser);
        ResponseModel responseModel = new ResponseModel(200, "200", "注销成功", null);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    private void toCache(AuthUser authUser) {
        this.stringRedisTemplate.opsForValue().set(TokenConstants.JTI_KEY_PREFIX + authUser.getJti(), "-1", authUser.getExpiredIn(), TimeUnit.MILLISECONDS);
    }

}
