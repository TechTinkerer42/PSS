
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
 *         &lt;element name="removeUserFromADGroupsReturn" type="{http://webservice.eias.ets.org}ResponseObject"/>
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
    "removeUserFromADGroupsReturn"
})
@XmlRootElement(name = "removeUserFromADGroupsResponse")
public class RemoveUserFromADGroupsResponse {

    @XmlElement(required = true)
    protected ResponseObject removeUserFromADGroupsReturn;

    /**
     * Gets the value of the removeUserFromADGroupsReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseObject }
     *     
     */
    public ResponseObject getRemoveUserFromADGroupsReturn() {
        return removeUserFromADGroupsReturn;
    }

    /**
     * Sets the value of the removeUserFromADGroupsReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseObject }
     *     
     */
    public void setRemoveUserFromADGroupsReturn(ResponseObject value) {
        this.removeUserFromADGroupsReturn = value;
    }

}
