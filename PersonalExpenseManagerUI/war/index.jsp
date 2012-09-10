<%@page import="com.pratikabu.pem.server.servlet.StaticHtmlResources"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="shortcut icon" href="static/favicon.ico">
		
		
		<link type="text/css" rel="stylesheet" href="static/stylesheet.css">
		<link type="text/css" rel="stylesheet" href="static/menustyles.css">
		
		<title>Bucks - Eighty_Coffee</title>
	
		<link rel="stylesheet" href="static/slider/light/light.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="static/slider/bar/bar.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="static/slider/nivo-slider.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="static/slider/style.css" type="text/css" media="screen" />
		
		<script type="text/javascript" src="static/slider/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="static/slider/jquery.nivo.slider.pack.js"></script>
		<script type="text/javascript">
			$(window).load(function() {
				$('#slider').nivoSlider();
			});
		</script>
	</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic UI.        -->
<!--                                           -->
<body class="zeroMargin">
	<div align="center" id="heardSlot" style="background: #F8F8F8; border-bottom: 1px solid #C5C5C5;">
		<div style="width: 900px; position: relative; overflow: hidden; padding-top: 5px; padding-bottom: 10px;">
			<div style="float: right; margin-bottom: 5px;"><%= StaticHtmlResources.getLoggedInHtml(session) %></div>
			<div style="width: 150px; float: left; clear: both;"><%= StaticHtmlResources.getIconHeader(true) %></div>
			<div style="width: 750px; float: right;"><%= StaticHtmlResources.getMenuHeader() %></div>
		</div>
	</div>
	<div id="mainContainer" align="center"
		style="width: 100%; padding-bottom: 87px; padding-top: 5px;">
		<div id="mainCenterContainer" style="width: 900px">
			<div id="wrapper" style="border-radius: 5px; border: 1px SOLID #C5C5C5; padding-bottom: 10px;">
				<div class="slider-wrapper theme-light">
					<div id="slider" class="nivoSlider">
						<a href="http://pratikabu.com"><img src="static/slider/toystory.jpg" data-thumb="images/toystory.jpg" data-transition="fade" /></a>
						<a href="http://pratikabu.com"><img src="static/slider/up.jpg" data-thumb="images/up.jpg" data-transition="fade"  /></a>
						<a href="http://pratikabu.com"><img src="static/slider/walle.jpg" data-thumb="images/walle.jpg" data-transition="fade" /></a>
						<a href="http://pratikabu.com"> <img src="static/slider/nemo.jpg" data-thumb="images/nemo.jpg" data-transition="fade" /></a>
					</div>
				</div>			
			</div>
			<div style="margin-top: 10px;"><a href="Dashboard.jsp" class="actionButton linkButton" style="padding: 10px 20px 10px 20px;">Launch Application</a></div>
		</div>
	</div>
	<%= StaticHtmlResources.getFooterHtml() %>
</body>
</html>
