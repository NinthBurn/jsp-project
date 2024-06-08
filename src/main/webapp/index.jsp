<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
  <style><%@include file="/WEB-INF/styles/main.css"%></style>
  <style><%@include file="/WEB-INF/styles/cool_background.css"%></style>
  
</head>
<body>
	<div class="animatedBackground">
		<h1><%= request.getContextPath() %></h1>
		<h1><%= "Hello World!" %></h1>
		<br/>
		<a href="register">Register here</a> <br>
		<a href="login">Login here</a> <br>
		<a href="topics">Topics</a> <br>
	</div>
</body>
</html>