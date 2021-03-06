<%@page import="com.pratikabu.pem.server.servlet.StaticHtmlResources"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="static/favicon.ico">
<title>Dashboard - Bucks</title>
<link rel="stylesheet" href="static/d-stylesheet.css">
<link rel="stylesheet" href="static/stylesheet.css">

<script type="text/javascript" language="javascript"
	src="dashboard/dashboard.nocache.js"></script>
</head>

<body class="zeroMargin">
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

	<div class="headerSlot"
		style="top: 0px; left: 0px; right: 0px; height: 34px;">
		<div align="left"
			style="position: absolute; top: 0px; left: 0px; right: 0px; width: 100%;">
			<table style="width: 100%;"><tr>
				<td align="center" style="width: 130px;"><%=StaticHtmlResources.getIconHeader(false)%></td>
				<td>
					<div id="cssmenu" style="width: 100%; position: relative;">
						<ul>
							<li class="has-sub "><a href="#"><span>Transactions</span></a>
								<ul>
									<li><a href="#" onclick="openRequest('tnip')"><span>New
												iPaid</span></a></li>
									<li><a href="#" onclick="openRequest('tnig')"><span>New
												iGot</span></a></li>
									<li><a href="#" onclick="openRequest('tnis')"><span>New
												iSaved</span></a></li>
									<li><a href="#" onclick="openRequest('tnsal')"><span>New
												Salary</span></a></li>
								</ul></li>
							<li class="has-sub "><a href="#"><span>Accounts</span></a>
								<ul>
									<li><a href="#" onclick="openRequest('ama')"><span>Manage
												Accounts</span></a></li>
									<li><a href="#" onclick="openRequest('anacc')"><span>New
												Account</span></a></li>
								</ul></li>
							<li class="has-sub "><a href="#"><span>Tools</span></a>
								<ul>
									<li><a href="#" onclick="openRequest('toaccsetting')"><span>Account
												Settings</span></a></li>
									<li><a href="#" onclick="openRequest('toart')"><span>Add
												Recursive Transaction</span></a></li>
								</ul></li>
							<li class="has-sub "><a href="#"><span>Reminders</span></a>
								<ul>
									<li><a href="#" onclick="openRequest('ryourself')"><span>Remind
												Yourself</span></a></li>
									<li><a href="#" onclick="openRequest('rprsn')"><span>Remind
												Person</span></a></li>
								</ul></li>
						</ul>
					</div>
				</td>
			</tr></table>
		</div>
	</div>
	<div id="mainContent" class="mainContent"
		style="bottom: 20px; top: 35px; width: 100%;">
		<div id="centerContainer" class="mainContent"
			style="bottom: 0px; top: 0px; margin-left: -450px; left: 50%; width: 900px;
			border-right: 1px SOLID #CBCBCB; border-left: 1px SOLID #CBCBCB; border-top: 1px SOLID #CBCBCB; border-radius: 5px 5px 0px 0px;">

			<div id="tgHeaderContainer" class="tgOuterDiv"
				style="top: 0px; left: 0px; width: 100%; height: 35px;">
				<div id="tgCurrentName" align="left"
					style="position: absolute; top: 0px; left: 0px; width: 85%; height: 35px; padding-top: 10px; padding-left: 5px;"></div>
				<div
					style="position: absolute; top: 0px; right: 0px; width: 15%; height: 35px;">
					<div id="tgMenu"
						style="position: absolute; top: 9px; right: 5px; bottom: 0px;">
						<input type="button" id="readerEdit" value="Filter"
							class="mybutton small red" style="margin-right: 5px;"
							onclick="openRequest('filter')">
					</div>
				</div>
			</div>
			<div id="txnListContainer" class="mainContent"
				style="bottom: 35px; top: 36px; left: 0px; width: 100%;"></div>
			<div id="totalContainer" class="mainContent"
				style="bottom: 0px; left: 0px; width: 100%; hegiht: 35px;">
				<table align="center" class="tgCellTable" cellspacing="0px"
					style="border-bottom: none; border-top: 1px SOLID #C5C5C5; cursor: default;"
					width="100%">
					<tr>
						<td align="left" class="tgCellTDStyle" width="50%">
							<div id="idPagination" class="normalLabel"
								style="padding-left: 5px;"></div>
						<td align="right" class="tgCellTDStyle" width="20%"><div
								class="normalLabel" style="padding-left: 5px;">Grand Total</div></td>
						<td align="right" class="tgCellTDStyle" width="15%"><div
								id="leftTotal" class="normalLabel"></div></td>
						<td align="right" class="tgCellTDStyle" width="15%"><div
								id="rightTotal" class="normalLabel" style="padding-right: 5px;"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div align="center" class="footerSlot" style="height: 20px;">
		<a href="http://www.pratikabu.com" title="Eighty_Coffee" style="vertical-align: middle;"><img src="static/eightycoffee.png" /></a>
		<span class="footerText"> 2012</span>
	</div>
</body>
</html>