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
package com.photowey.spring.in.action.controller;

import com.photowey.spring.in.action.annotation.MappingRestController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

import static com.photowey.spring.in.action.controller.MappingController.MAPPING_CONTROLLER_BEAN_NAME;

/**
 * {@code MappingController}
 *
 * @author photowey
 * @date 2023/06/18
 * @since 1.0.0
 */
@MappingRestController(value = MAPPING_CONTROLLER_BEAN_NAME, path = "/hello")
public class MappingController implements BeanFactoryAware {

    public static final String MAPPING_CONTROLLER_BEAN_NAME = "com.photowey.spring.in.action.controller.MappingController";

    private ListableBeanFactory beanFactory;

    // dependency cycle
    //@Autowired
    //@Qualifier(MAPPING_CONTROLLER_BEAN_NAME)
    //private MappingController mappingController;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    /**
     * <pre>
     * {
     * "status": "com.photowey.spring.in.action.controller.MappingController"
     * }
     * </pre>
     *
     * @see * http://127.0.0.1:7923/hello/world
     */
    @GetMapping("/world")
    public ResponseEntity<Status> healthz() {
        String[] beanNames = this.beanFactory.getBeanNamesForType(MappingController.class);
        return new ResponseEntity<>(Status.builder().status(StringUtils.arrayToDelimitedString(beanNames, ",")).build(), HttpStatus.OK);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Status implements Serializable {

        private String status;

        public static Status up() {
            return new Status("UP");
        }

        public static Status down() {
            return new Status("DOWN");
        }
    }

}
