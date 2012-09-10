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
<title>Login - Bucks</title>

<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->
<script type="text/javascript" language="javascript"
	src="welcome/welcome.nocache.js"></script>
</head>

</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic UI.        -->
<!--                                           -->
<body class="zeroMargin" style="background: #F8F8F8;">
	<!-- OPTIONAL: include this if you want history support -->
	<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
		style="position: absolute; width: 0; height: 0; border: 0"></iframe>

	<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
	<noscript>
		<div
			style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
			Your web browser must have JavaScript enabled in order for this
			application to display correctly.</div>
	</noscript>
	
	<div align="center" id="heardSlot">
		<div style="width: 900px; position: relative; overflow: hidden; padding-top: 5px; padding-bottom: 10px;">
			<div style="float: right; margin-bottom: 5px;"><%= StaticHtmlResources.getLoggedInHtml(session) %></div>
			<div style="width: 150px; float: left; clear: both;"><%= StaticHtmlResources.getIconHeader(true) %></div>
			<div style="width: 750px; float: right;"><%= StaticHtmlResources.getMenuHeader() %></div>
		</div>
	</div>
	<div id="mainContainer" align="center" style="width: 100%; padding-bottom: 87px;">
		<div id="loginFormContainer" style="padding-top:50px;"></div>
		<div id="errorLabel" class="errLabel" style="padding-top: 10px;"></div>
	</div>
	<%= StaticHtmlResources.getFooterHtml() %>
</body>
</html>

