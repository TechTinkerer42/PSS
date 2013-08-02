
package org.ets.ereg.external.service.eias.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppSysIDDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppSysIDDetails">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservice.eias.ets.org}BaseVO">
 *       &lt;sequence>
 *         &lt;element name="appSysID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="appSysIDPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppSysIDDetails", propOrder = {
    "appSysID",
    "appSysIDPassword"
})
public class AppSysIDDetails
    extends BaseVO
{

    @XmlElement(required = true, nillable = true)
    protected String appSysID;
    @XmlElement(required = true, nillable = true)
    protected String appSysIDPassword;

    /**
     * Gets the value of the appSysID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppSysID() {
        return appSysID;
    }

    /**
     * Sets the value of the appSysID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppSysID(String value) {
        this.appSysID = value;
    }

    /**
     * Gets the value of the appSysIDPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppSysIDPassword() {
        return appSysIDPassword;
    }

    /**
     * Sets the value of the appSysIDPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppSysIDPassword(String value) {
        this.appSysIDPassword = value;
    }

}
