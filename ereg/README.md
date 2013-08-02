## ETS eReg

This Maven project is used for the ETS Customer Profile Subsystem

> Note: 11/26/12 - This project is originally forked from the BLC Demo Site `master` branch (BLC 2.0.1)

## Functionality ##
The following provides a list of current RESTful endpoints exposed for this Customer Module
### Customer Endpoints ###
<table>
  <tr>
    <th>URI</th><td>Description</td><th>Method</th><th>Query Parameters</th>
  </tr>
  <tr>
    <td>/customer/{id}</td>
    <td>Accepts a Customer ID. Returns an ETSCustomerWrapper.</td>
    <td>GET</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/customer</td>
    <td>This should be called to find a customer by username or email. Returns an ETSCustomerWrapper</td>
    <td>GET</td>
    <td>String `username` or String `email`</td>
  </tr>
  <tr>
    <td>/customer</td>
    <td>This should be called to create or update a customer. Accepts an ETSCustomerWrapper representations in JSON or XML format. Returns an ETSCustomerWrapper</td>
    <td>PUT</td>
    <td>None</td>
  </tr>
</table>
### Customer Address Endpoints ###
<table>
  <tr>
    <th>URI</th><td>Description</td><th>Method</th><th>Query Parameters</th>
  </tr>
  <tr>
    <td>/customerAddress/{id}</td>
    <td>Accepts a Customer Address ID. Returns an ETSCustomerAddressWrapper.</td>
    <td>GET</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/customerAddress/customer/{id}</td>
    <td>This reads all ACTIVE customer addresses by customer ID. Returns a list of ETSCustomerAddressWrappers</td>
    <td>GET</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/customerAddress</td>
    <td>This should be called to create or update a customer address. Accepts an ETSCustomerAddressWrapper representations in JSON or XML format. Returns an ETSCustomerAddressWrapper</td>
    <td>PUT</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/customerAddress</td>
    <td>This should be called to delete a customer address. Accepts an ETSCustomerAddressWrapper representations in JSON or XML format. Returns 200 response code if successful</td>
    <td>DELETE</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/customerAddress/customer/{id}</td>
    <td>This makes the customer address default for the given customer ID. Accepts an ETSCustomerAddressWrapper representations in JSON or XML format as well as a Customer ID. Returns 200 response code if successful</td>
    <td>POST</td>
    <td>None</td>
  </tr>
</table>
### Address Endpoints ###
<table>
  <tr>
    <th>URI</th><td>Description</td><th>Method</th><th>Query Parameters</th>
  </tr>
  <tr>
    <td>/address/{id}</td>
    <td>Accepts an Address ID. Returns an ETSAddressWrapper.</td>
    <td>GET</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/address</td>
    <td>This should be called to create or update an address. Accepts an ETSAddressWrapper representations in JSON or XML format. Returns an ETSAddressWrapper</td>
    <td>PUT</td>
    <td>None</td>
  </tr>
</table>
### Customer Phone Endpoints ###
<table>
  <tr>
    <th>URI</th><td>Description</td><th>Method</th><th>Query Parameters</th>
  </tr>
  <tr>
    <td>/customerPhone/{id}</td>
    <td>Accepts a Customer Phone ID. Returns an ETSCustomerPhoneWrapper.</td>
    <td>GET</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/customerPhone/customer/{id}</td>
    <td>This reads all ACTIVE customer phones by customer ID. Returns a list of ETSCustomerPhoneWrappers</td>
    <td>GET</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/customerPhone</td>
    <td>This should be called to create or update a customer phone. Accepts an ETSCustomerPhoneWrapper representations in JSON or XML format. Returns an ETSCustomerPhoneWrapper</td>
    <td>PUT</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/customerPhone</td>
    <td>This should be called to delete a customer phone. Accepts an ETSCustomerPhoneWrapper representations in JSON or XML format. Returns 200 response code if successful</td>
    <td>DELETE</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/customerPhone/customer/{id}</td>
    <td>This makes the customer phone default for the given customer ID. Accepts an ETSCustomerPhoneWrapper representations in JSON or XML format as well as a Customer ID. Returns 200 response code if successful</td>
    <td>POST</td>
    <td>None</td>
  </tr>
</table>
### Role Endpoints ###
<table>
  <tr>
    <th>URI</th><td>Description</td><th>Method</th><th>Query Parameters</th>
  </tr>
  <tr>
    <td>/role/{name}</td>
    <td>Accepts a role name. Returns an ETSRoleWrapper.</td>
    <td>GET</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/role/customer/{id}</td>
    <td>This reads all customer roles by customer ID. Accepts a customer ID, Returns a list of ETSCustomerRoleWrappers</td>
    <td>GET</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/role</td>
    <td>This should be called to add a role to a customer. Accepts an ETSCustomerRoleWrapper representations in JSON or XML format. Returns 200 response code if successful </td>
    <td>POST</td>
    <td>None</td>
  </tr>
</table>
### State Endpoints ###
<table>
  <tr>
    <th>URI</th><td>Description</td><th>Method</th><th>Query Parameters</th>
  </tr>
  <tr>
    <td>/state</td>
    <td>Find all states or all states with the given abbreviation. Returns a list of StateWrappers</td>
    <td>GET</td>
    <td>String `abbreviation` (optional)</td>
  </tr>
  <tr>
    <td>/state/{abbreviation}</td>
    <td>Accepts an abbreviation, Returns a single StateWrapper</td>
    <td>GET</td>
    <td>None</td>
  </tr>
</table>
### Country Endpoints ###
<table>
  <tr>
    <th>URI</th><td>Description</td><th>Method</th><th>Query Parameters</th>
  </tr>
  <tr>
    <td>/country</td>
    <td>Find all countries. Returns a list of CountryWrappers</td>
    <td>GET</td>
    <td>None</td>
  </tr>
  <tr>
    <td>/country/{abbreviation}</td>
    <td>Accepts an abbreviation, Returns a single CountryWrapper</td>
    <td>GET</td>
    <td>None</td>
  </tr>
</table>