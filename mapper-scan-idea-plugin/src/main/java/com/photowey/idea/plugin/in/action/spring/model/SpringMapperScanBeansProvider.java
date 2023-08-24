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
package com.photowey.idea.plugin.in.action.spring.model;

import com.intellij.jam.JamStringAttributeElement;
import com.intellij.jam.reflect.JamStringAttributeMeta;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.ref.AnnotationChildLink;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.AnnotatedElementsSearch;
import com.intellij.spring.contexts.model.LocalModel;
import com.intellij.spring.model.CommonSpringBean;
import com.intellij.spring.model.custom.CustomLocalComponentsDiscoverer;
import com.intellij.spring.model.jam.converters.PackageJamConverter;
import com.intellij.spring.model.jam.stereotype.CustomSpringComponent;
import com.intellij.util.containers.ContainerUtil;
import com.photowey.idea.plugin.in.action.constant.AnnotationNameConstant;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 请参考 {@link com.intellij.spring.model.extensions.myBatis.SpringMyBatisBeansProvider}
 *
 * @author wangXin
 * @date 2022-06-10 10:17 AM
 * @since 1.0.0
 */
public class SpringMapperScanBeansProvider extends CustomLocalComponentsDiscoverer {

    public Set<PsiPackage> getMapperInterfacePackage(PsiClass configPsiClass) {
        Set<PsiPackage> returnList = new HashSet<>();
        JamStringAttributeMeta.Collection<Collection<PsiPackage>> attributeMeta =
                new JamStringAttributeMeta.Collection<>("value", new PackageJamConverter());
        AnnotationChildLink annotationChildLink = new AnnotationChildLink(AnnotationNameConstant.MAPPER_SCAN);
        PsiElementRef<PsiAnnotation> childRef = annotationChildLink.createChildRef(configPsiClass);
        for (JamStringAttributeElement<Collection<PsiPackage>> element : attributeMeta.getJam(childRef)) {
            if (element != null) {
                Collection<PsiPackage> psiPackages = element.getValue();
                if (psiPackages != null) {
                    ContainerUtil.addAllNotNull(returnList, psiPackages);
                }
            }
        }

        return returnList;
    }

    @Override
    public @NotNull Collection<CommonSpringBean> getCustomComponents(@NotNull LocalModel localModel) {
        Module module = localModel.getModule();
        Collection<CommonSpringBean> myBatisMappers = new HashSet<>();
        if (Objects.nonNull(module)) {
            Project project = module.getProject();
            GlobalSearchScope globalSearchScope = GlobalSearchScope.allScope(project);
            PsiClass mapperScanAnnotationClass = JavaPsiFacade.getInstance(project).findClass(AnnotationNameConstant.MAPPER_SCAN, globalSearchScope);
            if (Objects.nonNull(mapperScanAnnotationClass)) {
                Collection<PsiClass> psiClasses = AnnotatedElementsSearch.searchPsiClasses(mapperScanAnnotationClass, globalSearchScope).findAll();
                for (PsiClass configMapperScanClass : psiClasses) {
                    Set<PsiPackage> mapperInterfacePackage = this.getMapperInterfacePackage(configMapperScanClass);
                    for (PsiPackage psiPackage : mapperInterfacePackage) {
                        PsiClass[] classes = psiPackage.getClasses();
                        for (PsiClass mapperPsiClass : classes) {
                            myBatisMappers.add(new CustomSpringComponent(mapperPsiClass));
                        }
                    }
                }
            }
        }

        return myBatisMappers;
    }
}
