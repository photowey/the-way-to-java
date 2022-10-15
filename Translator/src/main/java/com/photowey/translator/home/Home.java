package com.photowey.translator.home;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.photowey.translator.App;
import com.photowey.translator.constant.TranslatorConstants;
import com.photowey.translator.crypto.CryptoJava;
import com.photowey.translator.property.TranslatorProperties;
import com.photowey.translator.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
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
        String userConf = userConfig();
        FileUtils.writeFile(userConf, JSON.toJSONString(homeData, JSONWriter.Feature.PrettyFormat).getBytes(StandardCharsets.UTF_8));
    }

    private static String userConfig() {
        String sysUH = System.getProperty("user.home");
        String userHome = sysUH + File.separator + ".translator";
        String userConf = userHome + File.separator + "translator.json";

        return userConf;
    }

    private static void checkConfig() {
        String userConf = userConfig();
        File file = new File(userConf);
        if (file.exists()) {
            try {
                String config = FileUtils.readJson(userConf);
                if (StringUtils.isNotBlank(config)) {
                    HomeData homeData = JSON.parseObject(config, HomeData.class);
                    TranslatorProperties translatorProperties = App.getConfigure().getTranslatorProperties();
                    translatorProperties.setAppId(CryptoJava.AES.PKCS5Padding.decrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, homeData.getConfig().getBaidu().getAppId()));
                    translatorProperties.setAppSecret(CryptoJava.AES.PKCS5Padding.decrypt(TranslatorConstants.TRANSLATOR_CONFIG_AES_KEY, homeData.getConfig().getBaidu().getAppSecret()));
                }
            } catch (Exception ignored) {
            }
        } else {
            file.getParentFile().mkdirs();
        }
    }
}
