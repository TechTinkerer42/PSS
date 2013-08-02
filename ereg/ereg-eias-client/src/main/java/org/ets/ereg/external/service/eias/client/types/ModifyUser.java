
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
 *         &lt;element name="userObj" type="{http://webservice.eias.ets.org}User"/>
 *         &lt;element name="appSysIdDetails" type="{http://webservice.eias.ets.org}AppSysIDDetails"/>
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
    "userObj",
    "appSysIdDetails"
})
@XmlRootElement(name = "modifyUser")
public class ModifyUser {

    @XmlElement(required = true)
    protected User userObj;
    @XmlElement(required = true)
    protected AppSysIDDetails appSysIdDetails;

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

    /**
     * Gets the value of the appSysIdDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AppSysIDDetails }
     *     
     */
    public AppSysIDDetails getAppSysIdDetails() {
        return appSysIdDetails;
    }

    /**
     * Sets the value of the appSysIdDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppSysIDDetails }
     *     
     */
    public void setAppSysIdDetails(AppSysIDDetails value) {
        this.appSysIdDetails = value;
    }

}
