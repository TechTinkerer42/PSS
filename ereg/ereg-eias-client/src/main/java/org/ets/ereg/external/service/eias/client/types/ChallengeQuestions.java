
package org.ets.ereg.external.service.eias.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChallengeQuestions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChallengeQuestions">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservice.eias.ets.org}BaseVO">
 *       &lt;sequence>
 *         &lt;element name="security" type="{http://webservice.eias.ets.org}ArrayOfSecurity"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChallengeQuestions", propOrder = {
    "security"
})
public class ChallengeQuestions
    extends BaseVO
{

    @XmlElement(required = true, nillable = true)
    protected ArrayOfSecurity security;

    /**
     * Gets the value of the security property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSecurity }
     *     
     */
    public ArrayOfSecurity getSecurity() {
        return security;
    }

    /**
     * Sets the value of the security property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSecurity }
     *     
     */
    public void setSecurity(ArrayOfSecurity value) {
        this.security = value;
    }

}
