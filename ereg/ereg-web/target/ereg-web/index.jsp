<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <html>
<body onload="CheckForLoadEvent()">

<script>
function CheckForLoadEvent()
{
  document.loginpage.submit();
}
</script>

<form:form name="loginpage" method="GET" action="/ereg-web/secure/home">

</form:form>

</body>
</html>



