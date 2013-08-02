<%@ page import="org.ets.ereg.csr.web.form.profile.TCAProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="ETS - eREG (Duplicate Account)">
	<h1>Duplicate Account Found</h1>
	<!-- Form Canvas starts here -->
	<form method='POST' action=${actionValue}>

		<div class="formContainer">
			<div class="create_form">
				<c:if test="${not empty error}">
					<div class="errorblock">
						${error} <br />
					</div>
				</c:if>
				<ul>
					<li><h1>You may have created an account before</h1></li>
					<li>Our record indicates that you may have an existing
						account.<br>To access your existing account, sign in as
						returning user below.
					</li>
					<li><h2>Returning User</h2></li>
					<li><label for="username">User Name:</label> <input
						type='text' name=${username } value='' required="required"
						id="username"></li>
					<li><label for="password">Password:</label> <input
						type='password' name=${password } required="required"
						id="password" /></li>
					<li></li>
				</ul>
			</div>
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="span6">
					<a class="submit" href="<c:url value='/tca/profile/'/>">Back</a>
				</div>
				<div class="span6">
					<span class="right" style="margin: -4px 0 0 0;"><button type="submit" name="submit"
							class="submit">Sign In</button></span>
				</div>
			</div>
			<a href="">Forgot Username</a> | <a href="">Forgot Password</a><br>
			<p></p>
			<p>
				If you want to modify your Personal Information,click Back to return
				to previous page.<br>If you need any help,contact[xxxx] or
				email [xxx]
			</p>
		</div>
	</form>
	<!--  Form Canvas ends here -->
</t:base>