package com.photowey.translator.extension;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@code TranslatorCache}
 *
 * @author photowey
 * @date 2022/10/17
 * @since 1.0.0
 */
@State(name = "translator.cache", storages = {@Storage(value = "translator-cache.xml")})
public class TranslatorCache implements PersistentStateComponent<TranslatorCache> {

    public Map<String, String> translateCache = new LinkedHashMap<>();

    public static TranslatorCache getInstance(Project project) {
        return project.getService(TranslatorCache.class);
    }

    @Override
    public @Nullable TranslatorCache getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TranslatorCache state) {
        if (state.translateCache == null) {
            return;
        }
        this.translateCache = state.translateCache;
    }

    public Map<String, String> getTranslateCache() {
        return translateCache;
    }
}