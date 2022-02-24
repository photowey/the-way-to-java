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
package com.photowey.validator.in.action.example.global;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code GlobalExceptionHandler}
 *
 * @author photowey
 * @date 2022/02/24
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler implements EnvironmentAware {

    private Environment environment;

    private static final String PROFILE_KEY = "spring.profiles.active";
    private static final String PROFILE_DEV = "dev";
    private static final UrlPathHelper HELPER = new UrlPathHelper();

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionModel> processSystemException(
            Exception exception, HttpServletRequest request, HttpServletResponse response) throws Exception {

        this.populateContentType(response);

        return this.handleSystemException(exception, request, response);
    }

    private void populateContentType(HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    private ResponseEntity<ExceptionModel> handleSystemException(
            Exception exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> messages = new ArrayList<>();
        if (exception instanceof MissingServletRequestParameterException) {
            this.handleParameterException((MissingServletRequestParameterException) exception, messages);
        } else if (exception instanceof MethodArgumentNotValidException) {
            this.handleValidException((MethodArgumentNotValidException) exception, messages);
        } else if (exception instanceof ValidationException) {
            this.handleValidException((ValidationException) exception, messages);
        } else {
            // env: dev
            // env: ...
            String profileActivated = this.environment.getProperty(PROFILE_KEY);
            if (StringUtils.isNotBlank(profileActivated) && StringUtils.equals(profileActivated, PROFILE_DEV)) {
                String lookupPath = HELPER.getLookupPathForRequest(request);
                messages.add("path:" + lookupPath);
                messages.add(exception.getMessage());
            } else {
                this.handleDefaultException(messages);
            }
        }

        // 1010 随便自定义的
        ExceptionModel exceptionModel = new ExceptionModel(HttpStatus.BAD_REQUEST.value(), 1010, String.join("#", messages));

        return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
    }

    private void handleDefaultException(List<String> messages) {
        messages.add("系统未能正确处理请求,请稍后重试~");
    }

    private void handleValidException(MethodArgumentNotValidException exception, List<String> messages) {
        BindingResult bindingResult = exception.getBindingResult();
        for (FieldError error : bindingResult.getFieldErrors()) {
            messages.add(error.getDefaultMessage());
        }
    }

    private void handleValidException(ValidationException exception, List<String> messages) {
        String message = null != exception.getCause() ? exception.getCause().getMessage() : "";
        if (StringUtils.isNotBlank(message)) {
            messages.add(message);
        }
    }

    private void handleParameterException(MissingServletRequestParameterException exception, List<String> messages) {
        messages.add("参数[" + exception.getParameterName() + "]不能为空");
    }
}