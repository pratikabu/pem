<%@page import="com.pratikabu.pem.server.servlet.StaticHtmlResources"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="shortcut icon" href="static/favicon.ico">


<!--                                                               -->
<!-- Consider inlining CSS to reduce the number of requested files -->
<!--                                                               -->
<link type="text/css" rel="stylesheet" href="static/stylesheet.css">
<link type="text/css" rel="stylesheet" href="static/menustyles.css">

<!--                                           -->
<!-- Any title is fine                         -->
<!--                                           -->
<title>Bucks - Eighty_Coffee</title>

</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic UI.        -->
<!--                                           -->
<body>
	<%= StaticHtmlResources.getIconHeader(true) %>
	<%= StaticHtmlResources.getMenuHeader() %>

	<table align="center">
		<tr>
			<td id="signUpFormContainer"></td>
		</tr>
	</table>
</body>
</html>
