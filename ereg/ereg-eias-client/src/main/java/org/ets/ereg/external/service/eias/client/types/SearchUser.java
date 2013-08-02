
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
 *         &lt;element name="attributeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attributeValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ldapQuery" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="searchBase" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "attributeName",
    "attributeValue",
    "ldapQuery",
    "searchBase"
})
@XmlRootElement(name = "searchUser")
public class SearchUser {

    @XmlElement(required = true)
    protected AppSysIDDetails appSysIDDetails;
    @XmlElement(required = true)
    protected String attributeName;
    @XmlElement(required = true)
    protected String attributeValue;
    @XmlElement(required = true)
    protected String ldapQuery;
    @XmlElement(required = true)
    protected String searchBase;

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
     * Gets the value of the attributeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Sets the value of the attributeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeName(String value) {
        this.attributeName = value;
    }

    /**
     * Gets the value of the attributeValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     * Sets the value of the attributeValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeValue(String value) {
        this.attributeValue = value;
    }

    /**
     * Gets the value of the ldapQuery property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLdapQuery() {
        return ldapQuery;
    }

    /**
     * Sets the value of the ldapQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLdapQuery(String value) {
        this.ldapQuery = value;
    }

    /**
     * Gets the value of the searchBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchBase() {
        return searchBase;
    }

    /**
     * Sets the value of the searchBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchBase(String value) {
        this.searchBase = value;
    }

}
