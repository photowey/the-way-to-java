package com.photowey.model;

import com.intellij.jam.JamStringAttributeElement;
import com.intellij.jam.reflect.JamStringAttributeMeta;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementRef;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.ref.AnnotationChildLink;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.AnnotatedElementsSearch;
import com.intellij.spring.contexts.model.LocalModel;
import com.intellij.spring.model.CommonSpringBean;
import com.intellij.spring.model.custom.CustomLocalComponentsDiscoverer;
import com.intellij.spring.model.jam.converters.PackageJamConverter;
import com.intellij.spring.model.jam.stereotype.CustomSpringComponent;
import com.intellij.util.containers.ContainerUtil;
import com.photowey.constant.AnnotationNameConstant;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * 请参考 {@link com.intellij.spring.model.extensions.myBatis.SpringMyBatisBeansProvider}
 *
 * @author wangXin
 * @version v1.0.0
 * @date 2022-06-10 10:17 AM
 */
public class SpringMapperScanBeansProvider extends CustomLocalComponentsDiscoverer {
    
    public Set<PsiPackage> getMapperInterfacePackage(PsiClass configPsiClass) {
        Set<PsiPackage> returnList = new HashSet<>();
        JamStringAttributeMeta.Collection<Collection<PsiPackage>> attributeMeta = new JamStringAttributeMeta.Collection(
                "value", new PackageJamConverter());
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
        Collection<CommonSpringBean> myBatisMappers = new HashSet();
        if (Objects.nonNull(module)) {
            Project project = module.getProject();
            GlobalSearchScope globalSearchScope = GlobalSearchScope.allScope(project);
            PsiClass mapperScanAnnotationClass = JavaPsiFacade.getInstance(project)
                    .findClass(AnnotationNameConstant.MAPPER_SCAN, globalSearchScope);
            if (Objects.nonNull(mapperScanAnnotationClass)) {
                Collection<PsiClass> psiClasses = AnnotatedElementsSearch.searchPsiClasses(mapperScanAnnotationClass,
                                globalSearchScope)
                        .findAll();
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
