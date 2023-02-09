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
package com.photowey.property.bind.in.action.binder;

import com.photowey.property.bind.in.action.App;
import com.photowey.property.bind.in.action.TestBase;
import com.photowey.property.bind.in.action.factory.YamlPropertySourceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code PropertyBindersTest}
 *
 * @author photowey
 * @date 2023/02/09
 * @since 1.0.0
 */
@SpringBootTest(classes = {App.class, PropertyBindersTest.Configure.class})
class PropertyBindersTest extends TestBase {

 @Configuration
 @PropertySource(value = "classpath:/hello-dev.yml", factory = YamlPropertySourceFactory.class)
 static class Configure {

 }

 @Test
 void testBind() {
  Map<String, Object> ctx = new HashMap<>(8);
  ctx.put("app.hello.name", "hello-tester");
  ctx.put("app.hello.profiles.active[0]", "dev");
  ctx.put("app.hello.profiles.active[1]", "webapp");
  PopupYamlProperties properties = PropertyBinders.bind(ctx, "app.hello", PopupYamlProperties.class);

  Assertions.assertNotNull(properties);
  Assertions.assertEquals("hello-tester", properties.getName());
  Assertions.assertEquals("dev", properties.getProfiles().getActive().get(0));
  Assertions.assertEquals("webapp", properties.getProfiles().getActive().get(1));
 }

 @Test
 void testMapBind_instance() {
  Map<String, Object> ctx = new HashMap<>(8);
  ctx.put("app.hello.profiles.active[0]", "dev");
  ctx.put("app.hello.profiles.active[1]", "webapp");

  PopupYamlProperties properties = new PopupYamlProperties();
  properties.setName("hello-tester");

  PropertyBinders.bind(ctx, "app.hello", properties);

  Assertions.assertNotNull(properties);
  Assertions.assertEquals("hello-tester", properties.getName());
  Assertions.assertEquals("dev", properties.getProfiles().getActive().get(0));
  Assertions.assertEquals("webapp", properties.getProfiles().getActive().get(1));
 }

 @Test
 void testEnvironmentBind() {
  Environment environment = this.applicationContext.getEnvironment();
  PopupYamlProperties properties = PropertyBinders.bind(environment, "app.hello", PopupYamlProperties.class);

  Assertions.assertNotNull(properties);
  Assertions.assertEquals("hello-tester", properties.getName());
  Assertions.assertEquals("dev", properties.getProfiles().getActive().get(0));
  Assertions.assertEquals("webapp", properties.getProfiles().getActive().get(1));
 }

}
