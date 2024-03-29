package org.ets.ereg.external.service.eias.client.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.0
 * Fri Dec 14 11:46:19 EST 2012
 * Generated source version: 2.3.0
 * 
 */
 
@WebService(targetNamespace = "http://webservice.eias.ets.org", name = "EIASWebService")
@XmlSeeAlso({org.ets.ereg.external.service.eias.client.types.ObjectFactory.class})
public interface EIASWebService {

    @WebResult(name = "addUserToADGroupsReturn", targetNamespace = "http://webservice.eias.ets.org")
    @RequestWrapper(localName = "addUserToADGroups", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.AddUserToADGroups")
    @WebMethod
    @ResponseWrapper(localName = "addUserToADGroupsResponse", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.AddUserToADGroupsResponse")
    public org.ets.ereg.external.service.eias.client.types.ResponseObject addUserToADGroups(
        @WebParam(name = "appSysIdDetails", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.AppSysIDDetails appSysIdDetails,
        @WebParam(name = "userId", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String userId,
        @WebParam(name = "objectGUID", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String objectGUID,
        @WebParam(name = "userADGrps", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.UserADGroups userADGrps
    );

    @WebResult(name = "modifyUserIdReturn", targetNamespace = "http://webservice.eias.ets.org")
    @RequestWrapper(localName = "modifyUserId", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.ModifyUserId")
    @WebMethod
    @ResponseWrapper(localName = "modifyUserIdResponse", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.ModifyUserIdResponse")
    public org.ets.ereg.external.service.eias.client.types.ResponseObject modifyUserId(
        @WebParam(name = "appSysIDDetails", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.AppSysIDDetails appSysIDDetails,
        @WebParam(name = "objectGuid", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String objectGuid,
        @WebParam(name = "existingUserId", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String existingUserId,
        @WebParam(name = "newUserId", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String newUserId
    );

    @WebResult(name = "createUserReturn", targetNamespace = "http://webservice.eias.ets.org")
    @RequestWrapper(localName = "createUser", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.CreateUser")
    @WebMethod
    @ResponseWrapper(localName = "createUserResponse", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.CreateUserResponse")
    public org.ets.ereg.external.service.eias.client.types.ResponseObject createUser(
        @WebParam(name = "userObj", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.User userObj,
        @WebParam(name = "appSysIdDetails", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.AppSysIDDetails appSysIdDetails
    );

    @WebResult(name = "resetPasswordReturn", targetNamespace = "http://webservice.eias.ets.org")
    @RequestWrapper(localName = "resetPassword", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.ResetPassword")
    @WebMethod
    @ResponseWrapper(localName = "resetPasswordResponse", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.ResetPasswordResponse")
    public org.ets.ereg.external.service.eias.client.types.ResponseObject resetPassword(
        @WebParam(name = "appSysIDDetails", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.AppSysIDDetails appSysIDDetails,
        @WebParam(name = "userId", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String userId,
        @WebParam(name = "objectGUID", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String objectGUID,
        @WebParam(name = "tempPassword", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String tempPassword,
        @WebParam(name = "emailNotificationFlag", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String emailNotificationFlag
    );

    @WebResult(name = "removeUserFromADGroupsReturn", targetNamespace = "http://webservice.eias.ets.org")
    @RequestWrapper(localName = "removeUserFromADGroups", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.RemoveUserFromADGroups")
    @WebMethod
    @ResponseWrapper(localName = "removeUserFromADGroupsResponse", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.RemoveUserFromADGroupsResponse")
    public org.ets.ereg.external.service.eias.client.types.ResponseObject removeUserFromADGroups(
        @WebParam(name = "appSysIdDetails", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.AppSysIDDetails appSysIdDetails,
        @WebParam(name = "userId", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String userId,
        @WebParam(name = "objectGUID", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String objectGUID,
        @WebParam(name = "userADGrps", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.UserADGroups userADGrps
    );

    @WebResult(name = "modifyUserReturn", targetNamespace = "http://webservice.eias.ets.org")
    @RequestWrapper(localName = "modifyUser", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.ModifyUser")
    @WebMethod
    @ResponseWrapper(localName = "modifyUserResponse", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.ModifyUserResponse")
    public org.ets.ereg.external.service.eias.client.types.ResponseObject modifyUser(
        @WebParam(name = "userObj", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.User userObj,
        @WebParam(name = "appSysIdDetails", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.AppSysIDDetails appSysIdDetails
    );

    @WebResult(name = "registerUserReturn", targetNamespace = "http://webservice.eias.ets.org")
    @RequestWrapper(localName = "registerUser", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.RegisterUser")
    @WebMethod
    @ResponseWrapper(localName = "registerUserResponse", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.RegisterUserResponse")
    public org.ets.ereg.external.service.eias.client.types.ResponseObject registerUser(
        @WebParam(name = "userObj", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.User userObj,
        @WebParam(name = "appSysIdDetails", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.AppSysIDDetails appSysIdDetails
    );

    @WebResult(name = "searchUserReturn", targetNamespace = "http://webservice.eias.ets.org")
    @RequestWrapper(localName = "searchUser", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.SearchUser")
    @WebMethod
    @ResponseWrapper(localName = "searchUserResponse", targetNamespace = "http://webservice.eias.ets.org", className = "org.ets.ereg.external.service.eias.client.types.SearchUserResponse")
    public org.ets.ereg.external.service.eias.client.types.ExtendedResponseObject searchUser(
        @WebParam(name = "appSysIDDetails", targetNamespace = "http://webservice.eias.ets.org")
        org.ets.ereg.external.service.eias.client.types.AppSysIDDetails appSysIDDetails,
        @WebParam(name = "attributeName", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String attributeName,
        @WebParam(name = "attributeValue", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String attributeValue,
        @WebParam(name = "ldapQuery", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String ldapQuery,
        @WebParam(name = "searchBase", targetNamespace = "http://webservice.eias.ets.org")
        java.lang.String searchBase
    );
}
