<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="./assets/css/common.css">
<link type="text/css" rel="stylesheet" href="./assets/css/base.css">
<script type="text/javascript">
function callback(){
	var url = window.location.href;
	var back = document.getElementById("callback");
	var index = url.indexOf("?");
	var callback = "";
	if(index > 0) {
		callback = url.substr(index + 1)
	}
	back.value = callback
}
</script>
</head>

<body onload="callback();">
<div class="header">
    <span class="logo"></span>
    <ul class="header-r">
        <li class="header-l">
            <a href="javascript:void (0);" class="header-l-a r-nav-1">帮助</a>
            <ul class="header-r-ul1">
                <span class="header-r-bg"></span>
                <li class="ul1-li-top"><a class="header-a" href="javascript:void (0);">常见问题</a></li>
                <li><a class="header-a" href="javascript:void (0);">在线学习</a></li>
                <li><a class="header-a" href="javascript:void (0);">客服咨询</a></li>
            </ul>
        </li>
        <li class="header-l">
            <a href="messageList.html" class="header-l-a r-nav-2" onclick="daschange('messageList.html');return false">消息</a>
        </li>
        <li class="header-l">
            <a href="javascript:void (0);" class="header-l-a r-nav-3">登录</a>
        </li>
    </ul>
</div>
<div class="login">
    <h1 class="login_h">登录<a href="register.html"><small id="register">注册</small></a></h1>
    <form action="./user/login.do" method="post">
    	<input type="hidden" id="callback" name="callback">
	    <div class="login_account">
	        <input type="text" name = "user_name" placeholder="email" class="input_login">
	        <span class="login_user"></span>
	    </div>
	    <div class="login_account">
	        <input type="password" name="pwd" placeholder="password" class="input_login">
	        <span class="login_pwd"></span>
	    </div>
	    <div class="login_account">
	        <a class="login_left login_a">忘记密码?</a>
	        <a class="login_right login_a">无法登录?</a>
	    </div>
	    <div class="login_account">
	        <input type="submit" class="btn-green btn" value="登录" />
	    </div>
    </form>
</div>

</body>
</html>