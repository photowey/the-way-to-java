
package com.photowey.webservice.client.in.action.webservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.photowey.webservice.client.in.action.webservice.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DATA_QNAME = new QName("http://server.webservice.service.action.in.webservice.photowey.com", "DATA");
    private final static QName _PARA_QNAME = new QName("http://server.webservice.service.action.in.webservice.photowey.com", "PARA");
    private final static QName _SayHello_QNAME = new QName("http://server.webservice.service.action.in.webservice.photowey.com", "sayHello");
    private final static QName _SayHelloResponse_QNAME = new QName("http://server.webservice.service.action.in.webservice.photowey.com", "sayHelloResponse");
    private final static QName _SayModel_QNAME = new QName("http://server.webservice.service.action.in.webservice.photowey.com", "sayModel");
    private final static QName _SayModelResponse_QNAME = new QName("http://server.webservice.service.action.in.webservice.photowey.com", "sayModelResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.photowey.webservice.client.in.action.webservice.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OpenapiResponse }
     * 
     */
    public OpenapiResponse createOpenapiResponse() {
        return new OpenapiResponse();
    }

    /**
     * Create an instance of {@link HelloPayload }
     * 
     */
    public HelloPayload createHelloPayload() {
        return new HelloPayload();
    }

    /**
     * Create an instance of {@link SayHello }
     * 
     */
    public SayHello createSayHello() {
        return new SayHello();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     * 
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link SayModel }
     * 
     */
    public SayModel createSayModel() {
        return new SayModel();
    }

    /**
     * Create an instance of {@link SayModelResponse }
     * 
     */
    public SayModelResponse createSayModelResponse() {
        return new SayModelResponse();
    }

    /**
     * Create an instance of {@link Hobby }
     * 
     */
    public Hobby createHobby() {
        return new Hobby();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OpenapiResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OpenapiResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice.service.action.in.webservice.photowey.com", name = "DATA")
    public JAXBElement<OpenapiResponse> createDATA(OpenapiResponse value) {
        return new JAXBElement<OpenapiResponse>(_DATA_QNAME, OpenapiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloPayload }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HelloPayload }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice.service.action.in.webservice.photowey.com", name = "PARA")
    public JAXBElement<HelloPayload> createPARA(HelloPayload value) {
        return new JAXBElement<HelloPayload>(_PARA_QNAME, HelloPayload.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice.service.action.in.webservice.photowey.com", name = "sayHello")
    public JAXBElement<SayHello> createSayHello(SayHello value) {
        return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice.service.action.in.webservice.photowey.com", name = "sayHelloResponse")
    public JAXBElement<SayHelloResponse> createSayHelloResponse(SayHelloResponse value) {
        return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME, SayHelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayModel }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SayModel }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice.service.action.in.webservice.photowey.com", name = "sayModel")
    public JAXBElement<SayModel> createSayModel(SayModel value) {
        return new JAXBElement<SayModel>(_SayModel_QNAME, SayModel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayModelResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SayModelResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice.service.action.in.webservice.photowey.com", name = "sayModelResponse")
    public JAXBElement<SayModelResponse> createSayModelResponse(SayModelResponse value) {
        return new JAXBElement<SayModelResponse>(_SayModelResponse_QNAME, SayModelResponse.class, null, value);
    }

}
