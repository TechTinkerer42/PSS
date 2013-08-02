<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<body onload="CheckForLoadEvent()">

<script>
function CheckForLoadEvent()
{
  document.loginpage.submit();
}
</script>

<form:form name="loginpage" method="GET" action="/ereg-csr-web/secure/home">

</form:form>
</body>
</html>