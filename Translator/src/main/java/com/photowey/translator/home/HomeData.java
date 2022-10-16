package com.photowey.translator.home;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@code HomeData}
 *
 * @author photowey
 * @date 2022/10/16
 * @since 1.0.0
 */
public class HomeData {

    private Config config = new Config();
    private Cache cache = new Cache();

    public HomeData() {
    }

    public HomeData(Config config) {
        this.config = config;
    }

    public static class Config implements Serializable {

        private static final long serialVersionUID = -8690787541357161767L;

        private Google google = new Google();
        private YouDao youDao = new YouDao();
        private Baidu baidu = new Baidu();

        public Config() {
        }

        public Config(Google google) {
            this.google = google;
        }

        public Config(YouDao youDao) {
            this.youDao = youDao;
        }

        public Config(Baidu baidu) {
            this.baidu = baidu;
        }

        public Google getGoogle() {
            return google;
        }

        public void setGoogle(Google google) {
            this.google = google;
        }

        public YouDao getYouDao() {
            return youDao;
        }

        public void setYouDao(YouDao youDao) {
            this.youDao = youDao;
        }

        public Baidu getBaidu() {
            return baidu;
        }

        public void setBaidu(Baidu baidu) {
            this.baidu = baidu;
        }

        public static class Google implements Serializable {

            private static final long serialVersionUID = -6319289664879582867L;

            private String appId;
            private String appSecret;

            public Google() {
            }

            public Google(String appId, String appSecret) {
                this.appId = appId;
                this.appSecret = appSecret;
            }

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getAppSecret() {
                return appSecret;
            }

            public void setAppSecret(String appSecret) {
                this.appSecret = appSecret;
            }
        }

        public static class YouDao implements Serializable {

            private static final long serialVersionUID = -7888549299907954504L;

            private String appId;
            private String appSecret;

            public YouDao() {
            }

            public YouDao(String appId, String appSecret) {
                this.appId = appId;
                this.appSecret = appSecret;
            }

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getAppSecret() {
                return appSecret;
            }

            public void setAppSecret(String appSecret) {
                this.appSecret = appSecret;
            }
        }

        public static class Baidu implements Serializable {

            private static final long serialVersionUID = -8690787541357161767L;

            private String appId;
            private String appSecret;

            public Baidu() {
            }

            public Baidu(String appId, String appSecret) {
                this.appId = appId;
                this.appSecret = appSecret;
            }

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getAppSecret() {
                return appSecret;
            }

            public void setAppSecret(String appSecret) {
                this.appSecret = appSecret;
            }
        }
    }

    public static class Cache implements Serializable {

        private Map<String, String> translateCache = new LinkedHashMap<>(1 << 4);

        public Map<String, String> getTranslateCache() {
            return translateCache;
        }

        public void put(String query, String cache) {
            this.translateCache.put(query, cache);
        }

        public String get(String query) {
            return this.translateCache.get(query);
        }
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
