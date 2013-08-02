
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
 *         &lt;element name="searchUserReturn" type="{http://webservice.eias.ets.org}ExtendedResponseObject"/>
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
    "searchUserReturn"
})
@XmlRootElement(name = "searchUserResponse")
public class SearchUserResponse {

    @XmlElement(required = true)
    protected ExtendedResponseObject searchUserReturn;

    /**
     * Gets the value of the searchUserReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ExtendedResponseObject }
     *     
     */
    public ExtendedResponseObject getSearchUserReturn() {
        return searchUserReturn;
    }

    /**
     * Sets the value of the searchUserReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtendedResponseObject }
     *     
     */
    public void setSearchUserReturn(ExtendedResponseObject value) {
        this.searchUserReturn = value;
    }

}
