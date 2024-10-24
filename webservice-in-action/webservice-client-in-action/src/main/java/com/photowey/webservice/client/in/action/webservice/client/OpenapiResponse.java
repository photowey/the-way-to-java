
package com.photowey.webservice.client.in.action.webservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;


/**
 * <p>openapiResponse complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="openapiResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://server.webservice.service.action.in.webservice.photowey.com}abstractResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BODY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "openapiResponse", propOrder = {
    "body"
}, namespace = "com.photowey.webservice.client.in.action.service.webservice.client.service.HelloWebService")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "DATA")
public class OpenapiResponse
    extends AbstractResponse {

    @XmlElement(name = "BODY", required = true)
    protected String body;

    /**
     * 获取body属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getBODY() {
        return body;
    }

    /**
     * 设置body属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBODY(String value) {
        this.body = value;
    }

}
