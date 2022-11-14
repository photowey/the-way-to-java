package com.photowey.plugin.xcurl.util;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.util.PsiTreeUtil;
import com.photowey.plugin.xcurl.constant.Constants;

/**
 * {@code ApiUtils}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class ApiUtils {

    public static String getPath(PsiMethod psiMethod) {
        StringBuilder result = new StringBuilder();
        PsiClass psiClass = PsiTreeUtil.getParentOfType(psiMethod, PsiClass.class);
        if (psiClass == null) {
            return "";
        }

        PsiAnnotation classAnnotation = psiClass.getAnnotation(Constants.RequestMapping);
        if (classAnnotation != null) {
            result.append(getAnnotationAttributeValue(classAnnotation, "value"));
        }

        for (PsiAnnotation annotation : psiMethod.getAnnotations()) {
            if (isMappingAnnotation(annotation)) {
                result.append(getAnnotationAttributeValue(annotation, "value"));
                break;
            }
        }
        return result.toString();
    }

    public static String getAnnotationAttributeValue(PsiAnnotation annotation, String attrName) {
        PsiNameValuePair[] attrs = annotation.getParameterList().getAttributes();
        for (PsiNameValuePair attr : attrs) {
            if (attr.getAttributeName().equals(attrName)) {
                return attr.getLiteralValue();
            }
        }
        return "";
    }

    public static boolean isMappingAnnotation(PsiAnnotation psiAnnotation) {
        return Constants.RequestMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.GetMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.PostMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.PutMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.DeleteMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.PatchMapping.equals(psiAnnotation.getQualifiedName());
    }
}