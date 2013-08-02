
package org.ets.ereg.external.service.eias.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="registerUserReturn" type="{http://webservice.eias.ets.org}ResponseObject"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "registerUserReturn"
})
@XmlRootElement(name = "registerUserResponse")
public class RegisterUserResponse {

    @XmlElement(required = true)
    protected ResponseObject registerUserReturn;

    /**
     * Gets the value of the registerUserReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseObject }
     *     
     */
    public ResponseObject getRegisterUserReturn() {
        return registerUserReturn;
    }

    /**
     * Sets the value of the registerUserReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseObject }
     *     
     */
    public void setRegisterUserReturn(ResponseObject value) {
        this.registerUserReturn = value;
    }

}
