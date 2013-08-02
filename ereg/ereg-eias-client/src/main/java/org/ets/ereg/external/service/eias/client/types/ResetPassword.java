
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
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectGUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tempPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="emailNotificationFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "userId",
    "objectGUID",
    "tempPassword",
    "emailNotificationFlag"
})
@XmlRootElement(name = "resetPassword")
public class ResetPassword {

    @XmlElement(required = true)
    protected AppSysIDDetails appSysIDDetails;
    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected String objectGUID;
    @XmlElement(required = true)
    protected String tempPassword;
    @XmlElement(required = true)
    protected String emailNotificationFlag;

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
     * Gets the value of the tempPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTempPassword() {
        return tempPassword;
    }

    /**
     * Sets the value of the tempPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTempPassword(String value) {
        this.tempPassword = value;
    }

    /**
     * Gets the value of the emailNotificationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailNotificationFlag() {
        return emailNotificationFlag;
    }

    /**
     * Sets the value of the emailNotificationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailNotificationFlag(String value) {
        this.emailNotificationFlag = value;
    }

}
