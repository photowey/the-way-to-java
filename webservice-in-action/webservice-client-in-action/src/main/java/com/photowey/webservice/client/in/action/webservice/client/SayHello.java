
package com.photowey.webservice.client.in.action.webservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>sayHello complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="sayHello"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="payload" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayHello", propOrder = {
    "payload"
}, namespace = "com.photowey.webservice.client.in.action.service.webservice.client.service.HelloWebService")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "PARA")
public class SayHello {

    protected String payload;

    /**
     * 获取payload属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getPayload() {
        return payload;
    }

    /**
     * 设置payload属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayload(String value) {
        this.payload = value;
    }

}
