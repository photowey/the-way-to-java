package com.photowey.http.in.action.context;

import com.photowey.http.in.action.enums.BodyType;
import com.photowey.http.in.action.enums.HttpMethod;
import com.photowey.http.in.action.query.RequestHeaders;
import com.photowey.http.in.action.query.RequestParameters;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.net.URL;

/**
 * {@code RequestContext}
 *
 * @author weichangjun
 * @date 2022/03/01
 * @since 1.0.0
 */
@Data
public class RequestContext implements Serializable {

    private String url;
    private String requestBody;
    private RequestHeaders headers = new RequestHeaders();
    private RequestParameters parameters = new RequestParameters();
    private HttpMethod httpMethod = HttpMethod.GET;
    private BodyType bodyType = BodyType.JSON;

    private Boolean newClient = Boolean.FALSE;

    public RequestContext() {
    }

    public RequestContext(String url) {
        this.url = url;
    }

    public RequestContext(String url, HttpMethod httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public RequestContext(String url, String requestBody, HttpMethod httpMethod) {
        this.url = url;
        this.requestBody = requestBody;
        this.httpMethod = httpMethod;
    }

    public BodyType getBodyType() {
        return null == bodyType ? BodyType.JSON : bodyType;
    }

    public RequestHeaders getHeaders() {
        return null == headers ? new RequestHeaders() : headers;
    }

    public RequestParameters getParameters() {
        return null == parameters ? new RequestParameters() : parameters;
    }

    public HttpMethod getHttpMethod() {
        return null == httpMethod ? HttpMethod.GET : httpMethod;
    }

    public String schema() {
        if (StringUtils.isEmpty(this.getUrl())) {
            return "";
        }
        try {
            URL url = new URL(this.getUrl());
            return url.getProtocol();
        } catch (Exception ignored) {
        }

        throw new RuntimeException(String.format("parse the url:[%s] for protocol exception", this.getUrl()));
    }

    public String host() {
        if (StringUtils.isEmpty(this.getUrl())) {
            return "";
        }
        try {
            URL url = new URL(this.getUrl());
            return url.getHost();
        } catch (Exception ignored) {
        }

        throw new RuntimeException(String.format("parse the url:[%s] for host exception", this.getUrl()));
    }

}
