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
package com.photowey.spring.in.action.defer;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * {@code HelloDeferredImportSelector}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
public class HelloDeferredImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{HelloDeferred.class.getName()};
    }

    @Override
    public Class<? extends Group> getImportGroup() {
        return HelloDeferredImportSelectorGroup.class;
    }

    private static class HelloDeferredImportSelectorGroup implements DeferredImportSelector.Group {

        private List<Entry> candidates = new ArrayList<>();

        @Override
        public void process(AnnotationMetadata metadata, DeferredImportSelector selector) {
            String[] selectImports = selector.selectImports(metadata);
            Stream.of(selectImports).forEach((candidateClassName) -> {
                this.candidates.add(new Entry(metadata, candidateClassName));
            });
        }

        @Override
        public Iterable<Entry> selectImports() {
            return this.candidates;
        }
    }
}
