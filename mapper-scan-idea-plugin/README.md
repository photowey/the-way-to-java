### gradle 命令

1. 插件仓库：[地址](https://plugins.gradle.org/)
2. 生成gradlew命令: `gradle wrapper`

### idea plugin

##### 查找属于那个模块

```
// 获取整个项目作用域
GlobalSearchScope scope = GlobalSearchScope.allScope(this.getModule().getProject());
// 通过类全名获取包名对应PsiPackage
PsiPackage package = JavaPsiFacade.getInstance(this.getModule().getProject()).findPackage(qualifiedName);
// 通过类全名获取对应的PsiClass
JavaPsiFacade.getInstance(this.getModule().getProject()).findClass(qualifiedName,GlobalSearchScope);


// 如FileInfoServiceImpl属于cl-flie-core模块
Module moduleForPsiElement=ModuleUtilCore.findModuleForPsiElement(method);

// 是否是Autowire注解修饰的类
SpringAutowireUtil.isAutowiredByAnnotation(field)

// SpringAutowiredAnnotator -> processVariable 处理构造函数的参数时，会通过以下方法查找依赖的bean
Collection<SpringBeanPointer<?>> list = SpringAutowireUtil.getAutowiredBeansFor(variable, getAutowiredType(type), model);

// MapperScan代码错误提示，提示检查类：SpringUastInjectionPointsAutowiringInspection -> checkByTypeAutowire
// CombinedSpringModelImpl
```
