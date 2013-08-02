
package org.ets.ereg.external.service.eias.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for User complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="User">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservice.eias.ets.org}BaseVO">
 *       &lt;sequence>
 *         &lt;element name="challengeQuestions" type="{http://webservice.eias.ets.org}ChallengeQuestions"/>
 *         &lt;element name="emailAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectGUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userADGroups" type="{http://webservice.eias.ets.org}UserADGroups"/>
 *         &lt;element name="userAttributes" type="{http://webservice.eias.ets.org}UserAttributes"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User", propOrder = {
    "challengeQuestions",
    "emailAddress",
    "firstName",
    "lastName",
    "objectGUID",
    "userADGroups",
    "userAttributes",
    "userId",
    "userType"
})
public class User
    extends BaseVO
{

    @XmlElement(required = true, nillable = true)
    protected ChallengeQuestions challengeQuestions;
    @XmlElement(required = true, nillable = true)
    protected String emailAddress;
    @XmlElement(required = true, nillable = true)
    protected String firstName;
    @XmlElement(required = true, nillable = true)
    protected String lastName;
    @XmlElement(required = true, nillable = true)
    protected String objectGUID;
    @XmlElement(required = true, nillable = true)
    protected UserADGroups userADGroups;
    @XmlElement(required = true, nillable = true)
    protected UserAttributes userAttributes;
    @XmlElement(required = true, nillable = true)
    protected String userId;
    @XmlElement(required = true, nillable = true)
    protected String userType;

    /**
     * Gets the value of the challengeQuestions property.
     * 
     * @return
     *     possible object is
     *     {@link ChallengeQuestions }
     *     
     */
    public ChallengeQuestions getChallengeQuestions() {
        return challengeQuestions;
    }

    /**
     * Sets the value of the challengeQuestions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChallengeQuestions }
     *     
     */
    public void setChallengeQuestions(ChallengeQuestions value) {
        this.challengeQuestions = value;
    }

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
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
     * Gets the value of the userADGroups property.
     * 
     * @return
     *     possible object is
     *     {@link UserADGroups }
     *     
     */
    public UserADGroups getUserADGroups() {
        return userADGroups;
    }

    /**
     * Sets the value of the userADGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserADGroups }
     *     
     */
    public void setUserADGroups(UserADGroups value) {
        this.userADGroups = value;
    }

    /**
     * Gets the value of the userAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link UserAttributes }
     *     
     */
    public UserAttributes getUserAttributes() {
        return userAttributes;
    }

    /**
     * Sets the value of the userAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAttributes }
     *     
     */
    public void setUserAttributes(UserAttributes value) {
        this.userAttributes = value;
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
     * Gets the value of the userType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserType(String value) {
        this.userType = value;
    }

}
