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
package com.photowey.elasticjob.in.action.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * {@code Knife4jConfigure}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Configuration
@EnableOpenApi
public class Knife4jConfigure {

    /**
     * 引入 {@code Knife4j} 提供的扩展类
     */
    private final OpenApiExtensionResolver openApiExtensionResolver;

    public Knife4jConfigure(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean(value = "elasticjob-in-action-docket")
    public Docket appDocket() {
        String group = "elasticjob-in-action";
        Contact contact = new Contact("photowey", "https://github.com/photowey", "photowey@gmail.com");
        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(this.populateApiInfo(contact))
                .groupName(group)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.photowey.elasticjob.in.action.controller"))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildExtensions(group));

        return docket;
    }

    private ApiInfo populateApiInfo(Contact contact) {
        return new ApiInfoBuilder()
                .title("elasticjob-in-action project")
                .description("# elasticjob-in-action project RESTful APIs")
                .termsOfServiceUrl("https://github.com/photowey/")
                .contact(contact)
                .version("1.0.0")
                .build();
    }
}