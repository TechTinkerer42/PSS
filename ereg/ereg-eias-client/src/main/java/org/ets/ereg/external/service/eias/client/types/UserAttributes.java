
package org.ets.ereg.external.service.eias.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserAttributes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserAttributes">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservice.eias.ets.org}BaseVO">
 *       &lt;sequence>
 *         &lt;element name="attribute" type="{http://webservice.eias.ets.org}ArrayOfAttribute"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserAttributes", propOrder = {
    "attribute"
})
public class UserAttributes
    extends BaseVO
{

    @XmlElement(required = true, nillable = true)
    protected ArrayOfAttribute attribute;

    /**
     * Gets the value of the attribute property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAttribute }
     *     
     */
    public ArrayOfAttribute getAttribute() {
        return attribute;
    }

    /**
     * Sets the value of the attribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAttribute }
     *     
     */
    public void setAttribute(ArrayOfAttribute value) {
        this.attribute = value;
    }

}
