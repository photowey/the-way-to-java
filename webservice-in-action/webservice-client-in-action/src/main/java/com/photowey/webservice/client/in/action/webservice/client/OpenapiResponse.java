
package com.photowey.webservice.client.in.action.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


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
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "openapiResponse", propOrder = {
    "body"
})
public class OpenapiResponse
    extends AbstractResponse {

    @XmlElement(name = "BODY", required = true)
    protected String body;

    /**
     * 获取body属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBODY() {
        return body;
    }

    /**
     * 设置body属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBODY(String value) {
        this.body = value;
    }

}
