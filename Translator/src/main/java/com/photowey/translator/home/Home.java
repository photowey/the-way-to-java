package com.photowey.translator.home;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.photowey.translator.App;
import com.photowey.translator.constant.TranslatorConstants;
import com.photowey.translator.crypto.CryptoJava;
import com.photowey.translator.extension.TranslatorSettings;
import com.photowey.translator.property.TranslatorProperties;
import com.photowey.translator.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * {@code Home}
 *
 * @author photowey
 * @date 2022/10/16
 * @since 1.0.0
 */
public class Home {

    public static void check() {
        checkConfig();
    }

    public static void init(String appId, String appSecret) {
        appId = CryptoJava.AES.PKCS5Padding.encrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, appId);
        appSecret = CryptoJava.AES.PKCS5Padding.encrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, appSecret);
        initConfig(appId, appSecret);
    }

    private static void initConfig(String appId, String appSecret) {
        HomeData.Config.Baidu baidu = new HomeData.Config.Baidu(appId, appSecret);
        HomeData homeData = new HomeData(new HomeData.Config(baidu));
        Pathx pathx = useHome();
        FileUtils.writeFile(pathx.getUserFilePath(), JSON.toJSONString(homeData, JSONWriter.Feature.PrettyFormat).getBytes(StandardCharsets.UTF_8));
    }

    private static Pathx useHome() {
        String sysUH = System.getProperty(TranslatorConstants.USER_HOME);
        String userHome = sysUH + File.separator + TranslatorConstants.TRANSLATOR_HOME;
        String userFile = TranslatorConstants.TRANSLATOR_FILE;

        return new Pathx(sysUH, userHome, userFile);
    }

    private static void checkConfig() {
        TranslatorProperties translatorProperties = App.getConfigure().getTranslatorProperties();
        TranslatorSettings translatorSettings = TranslatorSettings.getInstance();

        if (StringUtils.isNotBlank(translatorSettings.getAppId())) {
            translatorProperties.setAppId(translatorSettings.getAppId());
        }
        if (StringUtils.isNotBlank(translatorSettings.getAppSecret())) {
            translatorProperties.setAppSecret(translatorSettings.getAppSecret());
        }

        Pathx pathx = useHome();
        File file = new File(pathx.getUserFilePath());
        if (file.exists()) {
            try {
                if (StringUtils.isNotBlank(translatorSettings.getAppId()) &&
                        StringUtils.isNotBlank(translatorSettings.getAppSecret())) {
                    return;
                }
                String config = FileUtils.readJson(pathx.getUserHome(), pathx.getUserFile());
                if (StringUtils.isNotBlank(config)) {
                    HomeData homeData = JSON.parseObject(config, HomeData.class);
                    if (StringUtils.isBlank(translatorProperties.getAppId())) {
                        translatorProperties.setAppId(
                                CryptoJava.AES.PKCS5Padding.decrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, homeData.getConfig().getBaidu().getAppId())
                        );
                    }
                    if (StringUtils.isBlank(translatorProperties.getAppSecret())) {
                        translatorProperties.setAppSecret(
                                CryptoJava.AES.PKCS5Padding.decrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, homeData.getConfig().getBaidu().getAppSecret())
                        );
                    }
                }
            } catch (Exception ignored) {
            }
        } else {
            file.getParentFile().mkdirs();
        }
    }

    private static class Pathx implements Serializable {

        private static final long serialVersionUID = -8724339877896143522L;

        private String sysUH;
        private String userHome;
        private String userFile;

        public Pathx() {
        }

        public Pathx(String sysUH, String userHome, String userFile) {
            this.sysUH = sysUH;
            this.userHome = userHome;
            this.userFile = userFile;
        }

        public String getSysUH() {
            return sysUH;
        }

        public void setSysUH(String sysUH) {
            this.sysUH = sysUH;
        }

        public String getUserHome() {
            return userHome;
        }

        public void setUserHome(String userHome) {
            this.userHome = userHome;
        }

        public String getUserFile() {
            return userFile;
        }

        public String getUserFilePath() {
            return this.userHome + File.separator + userFile;
        }

        public void setUserFile(String userFile) {
            this.userFile = userFile;
        }
    }
}
