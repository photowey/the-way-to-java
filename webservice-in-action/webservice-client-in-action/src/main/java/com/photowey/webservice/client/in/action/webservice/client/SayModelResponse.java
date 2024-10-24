
package com.photowey.webservice.client.in.action.webservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;


/**
 * <p>sayModelResponse complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="sayModelResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://server.webservice.service.action.in.webservice.photowey.com}openapiResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayModelResponse", propOrder = {
    "_return"
}, namespace = "com.photowey.webservice.client.in.action.service.webservice.client.service.HelloWebService")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "sayModelResponse")
public class SayModelResponse {

    @XmlElement(name = "return")
    protected OpenapiResponse _return;

    /**
     * 获取return属性的值。
     *
     * @return possible object is
     * {@link OpenapiResponse }
     */
    public OpenapiResponse getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     *
     * @param value allowed object is
     *              {@link OpenapiResponse }
     */
    public void setReturn(OpenapiResponse value) {
        this._return = value;
    }

}
