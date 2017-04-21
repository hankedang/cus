<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CUS权限管理系统</title>
<link rel="stylesheet" type="text/css" href="./../../assets/easyui/themes/easyui.css" />
<link rel="stylesheet" type="text/css" href="./../../assets/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="./../../assets/css/main.css" />
</head>
<body class="easyui-layout">
	
	<!-- Header Logo -->
	<div class="header" data-options="region:'north'" style="height:51px">
		<div class="logo"></div>
		<ul class="nav">
			<li><a href="/cus/user/logout.do">安全退出</a></li>
		</ul>
	</div>
	
	<!-- Footer Copyright -->
	<div data-options="region:'south'" style="height:26px;">
		<div class="copyright">
			Copyright©版权所有，<a href="" target="_blank"></a>
		</div>
	</div>
	
	<!-- Left Menu -->
	<div data-options="region:'west'" style="width:200px;">
		<div class="easyui-accordion" data-options="fit:true,border:false,animate:false">
			<c:forEach var="pmenu" items="${ menu }">
				<div title="${ pmenu.text }" data-options="iconCls:'icon-tools'">
					<ul class="menu">
						<c:forEach var="cmenu" items="${ pmenu.children }">
						<li>
							<a href="javascript:void(0)" aim="/cus/${ cmenu.url }"><span class="${cmenu.iconCls}"></span>${ cmenu.text }</a>
						</li>
						</c:forEach>
					</ul>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<!-- Center Main Page -->
	<div id="mainPage" data-options="region:'center'">
		<div id="mainTabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="Welcome" closable="true" iconCls='icon-ok'>
				<iframe scrolling="auto" frameborder="0" src=""></iframe>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="./../../assets/jquery/jquery.min.js"></script>
<script type="text/javascript" src="./../../assets/easyui/easyui.min.js"></script>
<script type="text/javascript" src="./../../assets/easyui/easyui-lang-zh.js"></script>
<script type="text/javascript" src="./../../assets/js/main.js"></script>
</html>