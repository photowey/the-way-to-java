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
package com.photowey.drools.in.action.config;

import com.photowey.drools.in.action.bind.PropertyBinders;
import com.photowey.drools.in.action.core.util.Paths;
import com.photowey.drools.in.action.property.DroolsProperties;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * {@code DroolsAutoConfigure}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/11
 */
@AutoConfiguration
@Import(KModuleBeanFactoryPostProcessor.class)
public class DroolsAutoConfigure {

    private static final String DROOLS_RULE_ENGINE_ENV_PREFIX =
        DroolsProperties.DROOLS_RULE_ENGINE_ENV_PREFIX;
    private static final String DROOLS_RULE_ENGINE_PROPERTY_CUSTOM_PREFIX =
        DroolsProperties.DROOLS_RULE_ENGINE_PROPERTY_CUSTOM_PREFIX;

    @Bean
    @ConditionalOnMissingBean(DroolsProperties.class)
    public DroolsProperties droolsProperties(Environment environment) {
        return PropertyBinders.bind(
            environment,
            this.tryPropertyPrefix(environment),
            DroolsProperties.class
        );
    }

    // ----------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem(ResourcePatternResolver resolver, DroolsProperties props) {
        KieFileSystem kfs = this.kieServices().newKieFileSystem();

        for (Resource ruleFile : this.tryReadRuleFiles(resolver, props)) {
            String resourcePath = this.tryExtractRelativePath(ruleFile);
            org.kie.api.io.Resource kr = ResourceFactory.newClassPathResource(
                resourcePath,
                StandardCharsets.UTF_8.displayName()
            );
            kfs.write(kr);
        }

        return kfs;
    }

    // ----------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean(KieContainer.class)
    public KieContainer kieContainer(KieFileSystem kieFileSystem) {
        final KieRepository kieRepository = this.kieServices().getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieBuilder kieBuilder = this.kieServices().newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        return this.kieServices().newKieContainer(kieRepository.getDefaultReleaseId());
    }

    @Bean
    @ConditionalOnMissingBean(KieBase.class)
    public KieBase defaultKieBase(KieContainer kieContainer) {
        return kieContainer.getKieBase();
    }

    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public KieSession defaultKieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession();
    }

    // ----------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean(ResourcePatternResolver.class)
    public ResourcePatternResolver resourcePatternResolver() {
        return new PathMatchingResourcePatternResolver();
    }

    // ----------------------------------------------------------------

    private KieServices kieServices() {
        return KieServices.get();
    }

    // ----------------------------------------------------------------

    private String tryPropertyPrefix(Environment environment) {
        String prefix = System.getenv(DROOLS_RULE_ENGINE_ENV_PREFIX);
        if (Objects.nonNull(prefix)) {
            return prefix;
        }

        prefix = environment.getProperty(DROOLS_RULE_ENGINE_ENV_PREFIX);
        if (Objects.nonNull(prefix)) {
            return prefix;
        }

        prefix = environment.getProperty(DROOLS_RULE_ENGINE_PROPERTY_CUSTOM_PREFIX);
        if (Objects.nonNull(prefix)) {
            return prefix;
        }

        return DroolsProperties.getPrefix();
    }

    private Resource[] tryReadRuleFiles(ResourcePatternResolver resolver, DroolsProperties props) {
        try {
            return resolver.getResources(props.populateClasspathRuleResources());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String tryExtractRelativePath(Resource resource) {
        return Paths.tryExtractRelativePath(resource);
    }
}
