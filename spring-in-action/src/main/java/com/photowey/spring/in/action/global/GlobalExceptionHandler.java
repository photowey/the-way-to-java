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
package com.photowey.spring.in.action.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code GlobalExceptionHandler}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionModel> processSystemException(
            Exception exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.handleSystemException(exception, request, response);
    }

    private ResponseEntity<ExceptionModel> handleSystemException(
            Exception exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> messages = new ArrayList<>();
        if (exception instanceof MissingServletRequestParameterException) {
            this.handleParameterException((MissingServletRequestParameterException) exception, messages);
        } else if (exception instanceof MethodArgumentNotValidException) {
            this.handleValidException((MethodArgumentNotValidException) exception, messages);
        } else {
            this.handleDefaultException(messages);
        }
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

    private void handleParameterException(MissingServletRequestParameterException exception, List<String> messages) {
        messages.add("参数[" + exception.getParameterName() + "]不能为空");
    }
}
