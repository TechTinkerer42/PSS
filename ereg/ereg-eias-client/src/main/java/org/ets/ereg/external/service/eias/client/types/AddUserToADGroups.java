
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
 *         &lt;element name="appSysIdDetails" type="{http://webservice.eias.ets.org}AppSysIDDetails"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectGUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userADGrps" type="{http://webservice.eias.ets.org}UserADGroups"/>
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
    "appSysIdDetails",
    "userId",
    "objectGUID",
    "userADGrps"
})
@XmlRootElement(name = "addUserToADGroups")
public class AddUserToADGroups {

    @XmlElement(required = true)
    protected AppSysIDDetails appSysIdDetails;
    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected String objectGUID;
    @XmlElement(required = true)
    protected UserADGroups userADGrps;

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

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the objectGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectGUID() {
        return objectGUID;
    }

    /**
     * Sets the value of the objectGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectGUID(String value) {
        this.objectGUID = value;
    }

    /**
     * Gets the value of the userADGrps property.
     * 
     * @return
     *     possible object is
     *     {@link UserADGroups }
     *     
     */
    public UserADGroups getUserADGrps() {
        return userADGrps;
    }

    /**
     * Sets the value of the userADGrps property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserADGroups }
     *     
     */
    public void setUserADGrps(UserADGroups value) {
        this.userADGrps = value;
    }

}
