package com.photowey.translator.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.photowey.translator.App;
import com.photowey.translator.hash.Hash;
import com.photowey.translator.http.RequestExecutor;
import com.photowey.translator.http.model.RequestParameters;
import com.photowey.translator.property.TranslatorHttpProperties;
import com.photowey.translator.property.TranslatorProperties;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * {@code TranslateHandlerImpl}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class TranslateHandlerImpl implements TranslateHandler {

    private RequestExecutor requestExecutor;
    private TranslatorHttpProperties translatorHttpProperties;

    public TranslateHandlerImpl(RequestExecutor requestExecutor, TranslatorHttpProperties translatorHttpProperties) {
        this.requestExecutor = requestExecutor;
        this.translatorHttpProperties = translatorHttpProperties;
    }

    @Override
    public String handleTranslate(String query, String from, String to) {
        RequestParameters parameters = buildRequestParameters(query, from, to);
        String api = this.translatorHttpProperties.getTranslator().getApi();
        String response = this.requestExecutor.doGet(api, parameters);
        TranslateResponse translateResponse = JSON.parseObject(response, TranslateResponse.class);
        if (translateResponse.getTranslateResult() == null || translateResponse.getTranslateResult().size() == 0) {
            return "翻译出错";
        }

        return translateResponse.getTranslateResult().get(0).getDst();
    }

    private static RequestParameters buildRequestParameters(String query, String from, String to) {
        RequestParameters parameters = new RequestParameters();
        String q = urlEncode(query);
        parameters.add("q", q);
        parameters.add("from", from);
        parameters.add("to", to);
        TranslatorProperties translatorProperties = App.getConfigure().getTranslatorProperties();
        String appId = translatorProperties.getAppId();
        String appSecret = translatorProperties.getAppSecret();

        parameters.add("appid", translatorProperties.getAppId());

        String salt = String.valueOf(System.currentTimeMillis());
        parameters.add("salt", salt);
        String src = appId + query + salt + appSecret;
        parameters.add("sign", Hash.MD5.md5(src));

        return parameters;
    }

    private static String urlEncode(String url) {
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }

    public static class TranslateResponse {

        private String from;
        private String to;
        @JSONField(name = "trans_result")
        private List<TranslateResult> translateResult;

        public TranslateResponse() {
        }

        public TranslateResponse(String from, String to, List<TranslateResult> translateResult) {
            this.from = from;
            this.to = to;
            this.translateResult = translateResult;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public List<TranslateResult> getTranslateResult() {
            return translateResult;
        }

        public void setTranslateResult(List<TranslateResult> translateResult) {
            this.translateResult = translateResult;
        }
    }

    public static class TranslateResult {
        private String src;
        private String dst;

        public void setSrc(String src) {
            this.src = src;
        }

        public String getSrc() {
            return this.src;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }

        public String getDst() {
            return this.dst;
        }
    }
}
