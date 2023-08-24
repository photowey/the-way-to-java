/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.wechat.sdk.core.enums;

import com.photowey.wechat.sdk.core.resolver.PlaceholderResolver;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code WechatV3APIEnum}
 *
 * @author photowey
 * @date 2023/08/24
 * @since 1.0.0
 */
public enum WechatV3APIEnum {

    /**
     * 下载
     */
    DOWNLOAD(HttpMethod.GET, "${download}"),
    /**
     * 文件下载
     */
    FILE_DOWNLOAD(HttpMethod.GET, "${domain}/v3/billdownload/file"),

    /**
     * 获取证书
     */
    CERT(HttpMethod.GET, "${domain}/v3/certificates"),

    /**
     * 创建代金券批次 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_1.shtml">创建代金券批次 API</a>
     */
    MARKETING_FAVOR_COUPON_STOCKS(HttpMethod.POST, "${domain}/v3/marketing/favor/coupon-stocks"),
    /**
     * 激活代金券批次 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_3.shtml">激活代金券批次 API</a>
     */
    MARKETING_FAVOR_STOCKS_STOCKID_START(HttpMethod.POST, "${domain}/v3/marketing/favor/stocks/{stock_id}/start"),
    /**
     * 发放代金券批次 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_2.shtml">发放代金券批次 API</a>
     */
    MARKETING_FAVOR_USERS_OPENID_COUPONS(HttpMethod.POST, "${domain}/v3/marketing/favor/users/{openid}/coupons"),
    /**
     * 暂停代金券批次 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_13.shtml">暂停代金券批次 API</a>
     */
    MARKETING_FAVOR_STOCKS_STOCKID_PAUSE(HttpMethod.POST, "${domain}/v3/marketing/favor/stocks/{stock_id}/pause"),
    /**
     * 重启代金券 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_14.shtml">重启代金券 API</a>
     */
    MARKETING_FAVOR_STOCKS_STOCKID_RESTART(HttpMethod.POST, "${domain}/v3/marketing/favor/stocks/{stock_id}/restart"),
    /**
     * 条件查询批次列表 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_4.shtml">条件查询批次列表 API</a>
     */
    MARKETING_FAVOR_STOCKS_LIST_QUERY(HttpMethod.GET, "${domain}/v3/marketing/favor/stocks"),
    /**
     * 查询批次详情 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_5.shtml">查询批次详情 API</a>
     */
    MARKETING_FAVOR_STOCKS_STOCKID_DETAIL_QUERY(HttpMethod.GET, "${domain}/v3/marketing/favor/stocks/{stock_id}"),
    /**
     * 查询代金券详情 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_6.shtml">查询代金券详情 API</a>
     */
    MARKETING_FAVOR_USERS_OPENID_COUPONS_STOCKID_DETAIL_QUERY(HttpMethod.GET, "${domain}/v3/marketing/favor/users/{openid}/coupons/{coupon_id}"),
    /**
     * 查询代金券可用商户 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_7.shtml">查询代金券可用商户 API</a>
     */
    MARKETING_FAVOR_STOCKS_STOCKID_MERCHANTS_LIST_QUERY(HttpMethod.GET, "${domain}/v3/marketing/favor/stocks/{stock_id}/merchants"),
    /**
     * 查询代金券可用单品 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_8.shtml">查询代金券可用单品 API</a>
     */
    MARKETING_FAVOR_STOCKS_STOCKID_ITEMS_QUERY(HttpMethod.GET, "${domain}/v3/marketing/favor/stocks/{stock_id}/items"),
    /**
     * 下载批次核销明细 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_10.shtml">载批次核销明细 API</a>
     */
    MARKETING_FAVOR_STOCKS_STOCKID_USE_FLOW_DOWNLOAD(HttpMethod.GET, "${domain}/v3/marketing/favor/stocks/{stock_id}/use-flow"),
    /**
     * 下载批次退款明细 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_11.shtml">下载批次退款明细 API</a>
     */
    MARKETING_FAVOR_STOCKS_STOCKID_REFUND_FLOW_DOWNLOAD(HttpMethod.GET, "${domain}/v3/marketing/favor/stocks/{stock_id}/refund-flow"),
    /**
     * 设置核销回调通知 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_1_12.shtml">设置核销回调通知 API</a>
     */
    MARKETING_FAVOR_CALLBACKS_SET(HttpMethod.POST, "${domain}/v3/marketing/favor/callbacks"),

    // ----------------------------------------------------------------

    /**
     * 营销图片上传 API
     *
     * @see <a href="https://pay.wechat.qq.com/wiki/doc/apiv3_partner/apis/chapter9_0_1.shtml">营销图片上传 API</a>
     */
    MARKETING_FAVOR_MEDIA_IMAGE_UPLOAD(HttpMethod.POST, "${domain}/v3/marketing/favor/media/image-upload"),

    ;

    private final HttpMethod method;
    private final String pattern;

    WechatV3APIEnum(HttpMethod method, String pattern) {
        this.method = method;
        this.pattern = pattern;
    }

    public HttpMethod method() {
        return method;
    }

    public String pattern() {
        return pattern;
    }

    public String uri() {
        return this.uri(WechatServerEnum.CHINA);
    }

    public String uri(WechatServerEnum wechatServer) {
        Map<String, Object> ctx = new HashMap<>(2);

        return this.uri(wechatServer, ctx);
    }

    public String uri(Map<String, Object> ctx) {
        return PlaceholderResolver.$resolve(this.pattern, ctx);
    }

    public String uri(WechatServerEnum wechatServer, Map<String, Object> ctx) {
        ctx.put("domain", wechatServer.domain());

        return this.uri(ctx);
    }

}
