<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/view/common/taglibs.jsp" %>
<html>
<head>
<title>登录</title>
<%@ include file="/view/common/head.jsp" %>
<style type="text/css">
#container{
	width:800px;
	margin: 5px auto;
}
</style>
<script type="text/javascript">
$(function(){
	$('#username').focus();
});

function login(){
	var username = $('#username').val();
	if($.trim(username) == ''){
		alert('用户名不能为空！');
		return;
	}
	
	var url = '${path}/indexController/login.do';
	$.post(url, {username:username}, function(json){
		if(json.result == 'success'){
			location.href = '${path}/';
		}else{
			alert(json.result);
		}
	});
	
	return false;
}
</script>
</head>
<body>
<div id="container">


<div style="float:left;">
	<form onsubmit="return login();">
		用户名：<input id="username" name="username" type="text" />
		<input id="submitBut" type="button" value="登录" onclick="login();" />
	</form>
</div>

<div style="float:right;">
	<a href="${path}/">首页</a>
</div>
</div>
</body>
</html>