
package com.photowey.webservice.client.in.action.webservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>helloPayload complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="helloPayload"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="NAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AGE" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LIST" type="{http://server.webservice.service.action.in.webservice.photowey.com}hobby" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "helloPayload", propOrder = {
    "id",
    "name",
    "age",
    "list"
})
public class HelloPayload {

    @XmlElement(name = "ID")
    protected long id;
    @XmlElement(name = "NAME", required = true)
    protected String name;
    @XmlElement(name = "AGE")
    protected int age;
    @XmlElement(name = "LIST", required = true)
    protected List<Hobby> list;

    /**
     * 获取id属性的值。
     */
    public long getID() {
        return id;
    }

    /**
     * 设置id属性的值。
     */
    public void setID(long value) {
        this.id = value;
    }

    /**
     * 获取name属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getNAME() {
        return name;
    }

    /**
     * 设置name属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNAME(String value) {
        this.name = value;
    }

    /**
     * 获取age属性的值。
     */
    public int getAGE() {
        return age;
    }

    /**
     * 设置age属性的值。
     */
    public void setAGE(int value) {
        this.age = value;
    }

    /**
     * Gets the value of the list property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the list property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLIST().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Hobby }
     */
    public List<Hobby> getLIST() {
        if (list == null) {
            list = new ArrayList<Hobby>();
        }
        return this.list;
    }

}
