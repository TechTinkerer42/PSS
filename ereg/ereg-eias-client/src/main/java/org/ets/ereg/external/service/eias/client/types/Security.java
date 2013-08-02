
package org.ets.ereg.external.service.eias.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Security complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Security">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservice.eias.ets.org}BaseVO">
 *       &lt;sequence>
 *         &lt;element name="challengeA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="challengeQ" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Security", propOrder = {
    "challengeA",
    "challengeQ"
})
public class Security
    extends BaseVO
{

    @XmlElement(required = true, nillable = true)
    protected String challengeA;
    @XmlElement(required = true, nillable = true)
    protected String challengeQ;

    /**
     * Gets the value of the challengeA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChallengeA() {
        return challengeA;
    }

    /**
     * Sets the value of the challengeA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChallengeA(String value) {
        this.challengeA = value;
    }

    /**
     * Gets the value of the challengeQ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChallengeQ() {
        return challengeQ;
    }

    /**
     * Sets the value of the challengeQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChallengeQ(String value) {
        this.challengeQ = value;
    }

}
