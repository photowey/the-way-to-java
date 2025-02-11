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

import com.photowey.drools.in.action.bind.PropertyBinder;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

    @Bean
    public DroolsProperties droolsProperties(Environment environment) {
        return PropertyBinder.bind(
            environment,
            DroolsProperties.getPrefix(),
            DroolsProperties.class
        );
    }

    // ----------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem(ResourcePatternResolver resolver, DroolsProperties props) {
        KieFileSystem kfs = this.kieServices().newKieFileSystem();

        for (Resource ruleFile : this.tryReadRuleFiles(resolver, props)) {
            // String resourcePath = props.populateRuleFullPath(ruleFile.getFilename());
            String resourcePath = this.tryExtractRelativePath(ruleFile);

            org.kie.api.io.Resource kr =
                ResourceFactory.newClassPathResource(resourcePath, StandardCharsets.UTF_8.displayName());
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
    public KieBase defaultKieBase(KieFileSystem kieFileSystem) {
        return this.kieContainer(kieFileSystem).getKieBase();
    }

    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public KieSession defaultKieSession(KieFileSystem kieFileSystem) {
        return this.kieContainer(kieFileSystem).newKieSession();
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

    private Resource[] tryReadRuleFiles(ResourcePatternResolver resolver, DroolsProperties props) {
        try {
            return resolver.getResources(props.populateClasspathRuleResources());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String tryExtractRelativePath(Resource resource) {
        try {
            return this.extractRelativePath("", resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String extractRelativePath(String classpath, Resource resource) throws IOException {
        URL url = resource.getURL();
        String absolutePath = url.toString();

        if (absolutePath.startsWith("jar:")) {
            int startOfPath = absolutePath.indexOf("!/") + 2;
            return absolutePath.substring(startOfPath);
        }
        if (absolutePath.startsWith("file:")) {
            String classpathPrefix = "target/classes/";
            int index = absolutePath.indexOf(classpathPrefix);
            if (index != -1) {
                return absolutePath.substring(index + classpathPrefix.length()).replace("\\", "/");
            }

            return absolutePath;
        }

        throw new IllegalArgumentException("Unsupported resource URL protocol: " + url.getProtocol());
    }
}
