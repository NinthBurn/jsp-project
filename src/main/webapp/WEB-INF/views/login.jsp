<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login form</title>
    <style><%@include file="/WEB-INF/styles/cool_background.css"%></style>
    <style><%@include file="/WEB-INF/styles/main.css"%></style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/authentication.js" defer></script>
</head>
<body>
	<div class="animatedBackground">
	
		<div class="loginPanel">
		    <div class="loginForm">
		        <h1>Login Page</h1>
		        <form autocomplete="off" action="<%= request.getContextPath() %>/login" method="post" id="loginForm" >
		            <input type="hidden" id="action" value="login"/>
		
		            <label for="user_email">Email:</label><br><br>
		            <input id="user_email" type="text" name="user_email" required /><br>
		            <br>
		
		            <label for="user_password">Password:</label><br><br>
		            <input id="user_password" type="password" name="user_password" required /><br>
		            <br>

<%--		            <button type="button" onclick="loginUser()">Login</button>--%>
					<input type="submit" value="Login" />
					<br><br>
		        </form>
		        <div class="responseLabel"></div>
		        <div><br></div>
		 
			    <div style="width: 100%">
			        Don't have an account? Register <button type="button" class="registerLink" onclick="clickedRegisterLink()">here</button>.
			        <br>		
			    </div>

		    </div>
		</div>
	</div>
</body>
</html>