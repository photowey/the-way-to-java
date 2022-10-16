package com.photowey.translator.extension;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.photowey.translator.constant.TranslatorConstants;
import com.photowey.translator.crypto.CryptoJava;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@code TranslatorSettings}
 *
 * @author photowey
 * @date 2022/10/17
 * @since 1.0.0
 */
@State(name = "translator", storages = {@Storage(value = "translator.xml")})
public class TranslatorSettings implements PersistentStateComponent<TranslatorSettings> {

    public String appId;
    public String appSecret;

    public static TranslatorSettings getInstance() {
        return ApplicationManager.getApplication().getService(TranslatorSettings.class);
    }

    public static TranslatorSettings getInstance(Project project) {
        return project.getService(TranslatorSettings.class);
    }

    @Override
    public @Nullable TranslatorSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TranslatorSettings state) {
        this.appId = CryptoJava.AES.PKCS5Padding.decrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, state.getAppId());
        this.appSecret = CryptoJava.AES.PKCS5Padding.decrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, state.getAppSecret());
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = CryptoJava.AES.PKCS5Padding.encrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, appId);
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = CryptoJava.AES.PKCS5Padding.encrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, appSecret);
    }
}