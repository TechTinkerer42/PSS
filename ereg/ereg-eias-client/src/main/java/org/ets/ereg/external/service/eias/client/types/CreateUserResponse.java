
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
 *         &lt;element name="createUserReturn" type="{http://webservice.eias.ets.org}ResponseObject"/>
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
    "createUserReturn"
})
@XmlRootElement(name = "createUserResponse")
public class CreateUserResponse {

    @XmlElement(required = true)
    protected ResponseObject createUserReturn;

    /**
     * Gets the value of the createUserReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseObject }
     *     
     */
    public ResponseObject getCreateUserReturn() {
        return createUserReturn;
    }

    /**
     * Sets the value of the createUserReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseObject }
     *     
     */
    public void setCreateUserReturn(ResponseObject value) {
        this.createUserReturn = value;
    }

}
