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
/**
 * {@code org.springframework.boot.context.properties}
 * 解决异常报错
 * Error creating bean with name 'nacosConfigurationPropertiesBinder':
 * Bean instantiation via constructor failed; nested exception is org.springframework.beans.BeanInstantiationException:
 * Failed to instantiate [com.alibaba.boot.nacos.config.binder.NacosBootConfigurationPropertiesBinder]:
 * Constructor threw exception; nested exception is java.lang.NoClassDefFoundError: org/springframework/boot/context/properties/ConfigurationBeanFactoryMetadata
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
package org.springframework.boot.context.properties;