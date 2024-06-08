<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Register form</title>
    <style><%@include file="/WEB-INF/styles/cool_background.css"%></style>
    <style><%@include file="/WEB-INF/styles/main.css"%></style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/authentication.js" defer></script>
</head>
<body>
	<div class="animatedBackground">
	
		<div class="loginPanel">
		    <div class="loginForm">
		        <h1>Registration Form</h1>
		        <form autocomplete="off" action="<%= request.getContextPath() %>/register" method="post" id="registerForm">
		            <input type="hidden" id="action" value="register"/>
		
		            <label for="user_name">User Name:</label><br><br>
		            <input id="user_name" type="text" name="user_name" required><br>
		            <br>
		            
		            <label for="user_password">Password:</label><br><br>
		            <input id="user_password" type="password" name="user_password" required><br>
		            <br>
		
		            <label for="user_password_confirm">Confirm password:</label><br><br>
		            <input id="user_password_confirm" type="password" name="user_password_confirm" required><br>
		            <br>
		
		            <label for="user_email">Email:</label><br><br>
		            <input id="user_email" type="email" name="user_email" required><br>
		            <br><br>
		            
		            <input type="submit" value="Register User" />
		            <br><br>
		        </form>
		        
		        <div class="responseLabel">
		        	<%
					    if(null!=request.getAttribute("errorMessage"))
					    {
					        out.println(request.getAttribute("errorMessage"));
					    }
					%>
		        </div>
		        <div><br></div>
		    </div>
		</div>
	</div>
    <%-- <h1><%= request.getContextPath() %></h1>

    <div class="loginPanel" align="center">
        <form class="loginForm" action="<%= request.getContextPath() %>/register"  method="post">
            Name: <input type="text"  name="user_name" /> <br/> <br/>
            Email: <input type="text"  name="user_email" /> <br/> <br/>
            Password: <input type="text"  name="user_password" /> <br/> <br/>
            Confirm password: <input type="text"  name="user_confirm_password" /> <br/> <br/>
            <input type="submit"  value="RegisterUser" />
        </form>
    </div> --%>
</body>
</html>