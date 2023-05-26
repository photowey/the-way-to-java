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
package com.photowey.knife4j.in.action.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.google.common.net.HttpHeaders;
import com.photowey.knife4j.in.action.properties.Knife4jStarterProperties;
import com.photowey.swagger.provider.ext.annotation.EnableSwaggerProviderExt;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@code Knife4jAutoConfigure}
 *
 * @author photowey
 * @date 2022/01/14
 * @since 1.0.0
 */
@Configuration
@EnableSwaggerProviderExt
@Import(BeanValidatorPluginsConfiguration.class)
@EnableConfigurationProperties(value = {Knife4jStarterProperties.class})
public class Knife4jAutoConfigure implements EnvironmentAware {

    @Autowired(required = false)
    private OpenApiExtensionResolver openApiExtensionResolver;
    @Autowired
    private Knife4jStarterProperties knife4JProperties;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean("autoDocket")
    @ConditionalOnMissingBean(Docket.class)
    public Docket autoDocket() {
        Knife4jStarterProperties.Project project = this.knife4JProperties.getProject();
        Knife4jStarterProperties.Api api = this.knife4JProperties.getApi();
        String group = api.getGroup();
        List<String> controllers = api.getControllers();
        Contact contact = new Contact(
                project.getAuthor(),
                "https://wiki.photowey.dev/developer?auther=" + project.getAuthor(),
                project.getEmail()
        );
        Docket docket = new Docket(DocumentationType.OAS_30)
                .enable(this.docEnabled())
                .apiInfo(this.populateApiInfo(contact))
                // Support timestamp
                .directModelSubstitute(LocalDateTime.class, Long.class)
                .groupName(group)
                .select()
                .apis(basePackages(controllers))
                .paths(PathSelectors.any())
                .build();
        if (null != this.openApiExtensionResolver) {
            docket.extensions(this.openApiExtensionResolver.buildExtensions(group));
        }
        if (api.isEnableSecurity()) {
            docket.securitySchemes(this.securitySchemes())
                    .securityContexts(this.securityContexts());
        }

        // this.determineGlobalRequestParameters(docket);

        return docket;
    }

    private void determineGlobalRequestParameters(Docket docket) {
        List<RequestParameter> parameters = this.determineParameters();
        if (ObjectUtils.isNotEmpty(parameters)) {
            docket.globalRequestParameters(parameters);
        }
    }

    private ApiInfo populateApiInfo(Contact contact) {
        Knife4jStarterProperties.Api api = this.knife4JProperties.getApi();
        return new ApiInfoBuilder()
                .title(ObjectUtils.defaultIfNull(api.getTitle(), String.format("%s project", this.appName())))
                .description(ObjectUtils.defaultIfNull(api.getDescription(), String.format("# %s project RESTful APIs", this.appName())))
                .termsOfServiceUrl(ObjectUtils.defaultIfNull(api.getTermsOfServiceUrl(), "https://wiki.photowey.dev/"))
                .contact(contact)
                .version(ObjectUtils.defaultIfNull(api.getVersion(), "1.0.0"))
                .build();
    }

    private String pathMapping(String app) {
        return "/" + app;
    }

    public boolean docEnabled() {
        return !Boolean.parseBoolean(this.environment.getProperty("knife4j.production"));
    }

    private String appName() {
        return this.environment.getProperty("spring.application.name");
    }

    private static Predicate<RequestHandler> basePackages(final List<String> basePackages) {
        return (input) -> {
            return (Boolean) declaringClass(input).map(handlerPackages(basePackages)).orElse(true);
        };
    }

    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

    private static Function<Class<?>, Boolean> handlerPackages(final List<String> basePackages) {
        return (input) -> {

            for (String basePackage : basePackages) {
                if (ClassUtils.getPackageName(input).startsWith(basePackage)) {
                    return true;
                }
            }

            return false;

        };
    }

    // ----------------------------------------------------------------

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER.name());
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contexts = new ArrayList<>();
        Knife4jStarterProperties.Api api = this.knife4JProperties.getApi();
        api.getPathRegexes().forEach((pathRegex) -> contexts.add(this.populateContext(pathRegex)));

        return contexts;
    }

    private SecurityContext populateContext(String pathRegex) {
        return SecurityContext
                .builder()
                .securityReferences(defaultReferences())
                .operationSelector(ctx -> ctx.requestMappingPattern().matches(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultReferences() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")};
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes));
        return securityReferences;
    }

    private List<RequestParameter> determineParameters() {
        RequestParameterBuilder builder = new RequestParameterBuilder();
        List<RequestParameter> pars = new ArrayList<>();
        builder.name("x-ut").description("认证令牌")
                .in(ParameterType.HEADER)
                .required(false)
                .build();
        pars.add(builder.build());

        return pars;

    }
}