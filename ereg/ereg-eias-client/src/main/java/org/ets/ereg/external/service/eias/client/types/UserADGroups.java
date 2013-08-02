
package org.ets.ereg.external.service.eias.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserADGroups complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserADGroups">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservice.eias.ets.org}BaseVO">
 *       &lt;sequence>
 *         &lt;element name="adGroups" type="{http://webservice.eias.ets.org}ArrayOfADGroups"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserADGroups", propOrder = {
    "adGroups"
})
public class UserADGroups
    extends BaseVO
{

    @XmlElement(required = true, nillable = true)
    protected ArrayOfADGroups adGroups;

    /**
     * Gets the value of the adGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfADGroups }
     *     
     */
    public ArrayOfADGroups getAdGroups() {
        return adGroups;
    }

    /**
     * Sets the value of the adGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfADGroups }
     *     
     */
    public void setAdGroups(ArrayOfADGroups value) {
        this.adGroups = value;
    }

}
