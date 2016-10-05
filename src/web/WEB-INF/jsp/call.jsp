<%@ taglib uri="struts-tiles" prefix="tiles" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-logic" prefix="logic" %>

<h2>Submit Abstract</h2>
<p>Enterprise Java Conference is looking for the best Java practitioners to present 
  current and valueable topics to experience Java developers. If you think you 
  have such a topic submit it now and we will let you know if your topic has been 
  selected.
</p>

<logic:messagesPresent>
   <bean:message key="errors.header"/>
   <ul>
   <html:messages id="error">
      <li><bean:write name="error"/></li>
   </html:messages>
   </ul><hr>
</logic:messagesPresent>

<html:form action="submitPresenter.do">

 <table border="0" width="100%">
  <tr>
   <td align="right" width="30%">
    First Name:
   </td>
   <td align="left">
    <html:text property="firstname" size="30"/>
   </td>
  </tr>
  <tr>
   <td align="right" width="30%">
    Last Name:
   </td>
   <td align="left">
    <html:text property="lastname" size="30"/>
   </td>
  </tr>
  <tr>
   <td align="right" width="30%">
    Email:
   </td>
   <td align="left">
    <html:text property="email" size="30"/>
   </td>
  </tr>
  <tr>
   <td align="right" width="30%">
    Password:
   </td>
   <td align="left">
    <html:text property="password" size="30"/>
   </td>
  </tr>
  <tr>
   <td align="right" width="30%">
    Home Phone:
   </td>
   <td align="left">
    <html:text property="homephone" size="14"/>
   </td>
  </tr>
  <tr>
   <td align="right" width="30%">
    Work Phone:
   </td>
   <td align="left">
    <html:text property="workphone" size="14"/>
   </td>
  </tr>
  <tr>
   <td align="right" width="30%">
    Fax:
   </td>
   <td align="left">
    <html:text property="fax" size="14"/>
   </td>
  </tr>
  <tr>
   <td align="right" width="30%">
    Street:
   </td>
   <td align="left">
    <html:text property="street" size="50"/>
   </td>
  </tr>
  <tr>
   <td align="right" width="30%">
     City:
   </td>
   <td align="left">
    <html:text property="city" size="20"/>
    State: <html:text property="state" size="2"/>
    Zip: <html:text property="zip" size="10"/>
   </td>
  </tr>


  <tr>
   <td align="right">
    <html:submit>Submit</html:submit>
   </td>
   <td align="left">
    <html:reset>Reset</html:reset>
   </td>
  </tr>
 </table>
			   
</html:form>