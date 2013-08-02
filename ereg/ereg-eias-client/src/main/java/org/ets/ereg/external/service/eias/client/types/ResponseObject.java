
package org.ets.ereg.external.service.eias.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResponseObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseObject">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservice.eias.ets.org}BaseVO">
 *       &lt;sequence>
 *         &lt;element name="respCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="respMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="respResult" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="returnAttribute" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userADObjGUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userIdSuggestions" type="{http://webservice.eias.ets.org}ArrayOf_xsd_string"/>
 *         &lt;element name="userObj" type="{http://webservice.eias.ets.org}User"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseObject", propOrder = {
    "respCode",
    "respMessage",
    "respResult",
    "returnAttribute",
    "userADObjGUID",
    "userIdSuggestions",
    "userObj"
})
@XmlSeeAlso({
    ExtendedResponseObject.class
})
public class ResponseObject
    extends BaseVO
{

    @XmlElement(required = true, nillable = true)
    protected String respCode;
    @XmlElement(required = true, nillable = true)
    protected String respMessage;
    @XmlElement(required = true, nillable = true)
    protected String respResult;
    @XmlElement(required = true, nillable = true)
    protected String returnAttribute;
    @XmlElement(required = true, nillable = true)
    protected String userADObjGUID;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfXsdString userIdSuggestions;
    @XmlElement(required = true, nillable = true)
    protected User userObj;

    /**
     * Gets the value of the respCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * Sets the value of the respCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespCode(String value) {
        this.respCode = value;
    }

    /**
     * Gets the value of the respMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespMessage() {
        return respMessage;
    }

    /**
     * Sets the value of the respMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespMessage(String value) {
        this.respMessage = value;
    }

    /**
     * Gets the value of the respResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespResult() {
        return respResult;
    }

    /**
     * Sets the value of the respResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespResult(String value) {
        this.respResult = value;
    }

    /**
     * Gets the value of the returnAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnAttribute() {
        return returnAttribute;
    }

    /**
     * Sets the value of the returnAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnAttribute(String value) {
        this.returnAttribute = value;
    }

    /**
     * Gets the value of the userADObjGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserADObjGUID() {
        return userADObjGUID;
    }

    /**
     * Sets the value of the userADObjGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserADObjGUID(String value) {
        this.userADObjGUID = value;
    }

    /**
     * Gets the value of the userIdSuggestions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfXsdString }
     *     
     */
    public ArrayOfXsdString getUserIdSuggestions() {
        return userIdSuggestions;
    }

    /**
     * Sets the value of the userIdSuggestions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfXsdString }
     *     
     */
    public void setUserIdSuggestions(ArrayOfXsdString value) {
        this.userIdSuggestions = value;
    }

    /**
     * Gets the value of the userObj property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUserObj() {
        return userObj;
    }

    /**
     * Sets the value of the userObj property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUserObj(User value) {
        this.userObj = value;
    }

}
