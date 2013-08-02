
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
 *         &lt;element name="appSysIDDetails" type="{http://webservice.eias.ets.org}AppSysIDDetails"/>
 *         &lt;element name="objectGuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="existingUserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newUserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "appSysIDDetails",
    "objectGuid",
    "existingUserId",
    "newUserId"
})
@XmlRootElement(name = "modifyUserId")
public class ModifyUserId {

    @XmlElement(required = true)
    protected AppSysIDDetails appSysIDDetails;
    @XmlElement(required = true)
    protected String objectGuid;
    @XmlElement(required = true)
    protected String existingUserId;
    @XmlElement(required = true)
    protected String newUserId;

    /**
     * Gets the value of the appSysIDDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AppSysIDDetails }
     *     
     */
    public AppSysIDDetails getAppSysIDDetails() {
        return appSysIDDetails;
    }

    /**
     * Sets the value of the appSysIDDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppSysIDDetails }
     *     
     */
    public void setAppSysIDDetails(AppSysIDDetails value) {
        this.appSysIDDetails = value;
    }

    /**
     * Gets the value of the objectGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectGuid() {
        return objectGuid;
    }

    /**
     * Sets the value of the objectGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectGuid(String value) {
        this.objectGuid = value;
    }

    /**
     * Gets the value of the existingUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistingUserId() {
        return existingUserId;
    }

    /**
     * Sets the value of the existingUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistingUserId(String value) {
        this.existingUserId = value;
    }

    /**
     * Gets the value of the newUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewUserId() {
        return newUserId;
    }

    /**
     * Sets the value of the newUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewUserId(String value) {
        this.newUserId = value;
    }

}
