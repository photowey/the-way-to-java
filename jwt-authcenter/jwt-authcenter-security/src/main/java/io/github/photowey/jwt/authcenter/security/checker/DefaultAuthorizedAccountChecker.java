/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.security.checker;

import io.github.photowey.jwt.authcenter.core.checker.AbstractAuthenticatedExceptionChecker;
import io.github.photowey.jwt.authcenter.core.constant.MessageConstants;
import io.github.photowey.jwt.authcenter.core.domain.authorized.AuthorizedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * {@code DefaultAuthorizedAccountChecker}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/06
 */
@Slf4j
@Component
public class DefaultAuthorizedAccountChecker extends AbstractAuthorizedAccountChecker {

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public boolean supports(String checker) {
        return true;
    }

    @Override
    public void check(AuthorizedUser authorized) {
        this.checkUserStatus(authorized);
        this.checkAuthorizeStatus(authorized);
    }

    private void checkUserStatus(AuthorizedUser authorized) {
        boolean activated = authorized.determineIsActivated();
        AbstractAuthenticatedExceptionChecker.checkTrue(
            activated, MessageConstants.AUTHORIZE_ACCOUNT_NOT_ACTIVATE);
    }

    private void checkAuthorizeStatus(AuthorizedUser authorized) {
        // NEXT: 待扩展
    }
}

