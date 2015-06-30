<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/view/common/taglibs.jsp" %>
<html>
<head>
<title>粉丝</title>
<%@ include file="/view/common/head.jsp" %>
<script type="text/javascript">
$(function(){
});

</script>
</head>
<body>

<!-- 固定导航 -->
<%@ include file="/view/common/top.jsp" %>

<div id="container">

${username}的关注

<div>
	<hr/>
	<c:forEach var="user" items="${userList}">
		<div>
			<span style="float: left;">${user.username}</span>
			<span style="float: right;">${user.createTime }</span>
			<div style="clear: both;"></div>
		</div>
		<hr/>
	</c:forEach>
</div>

</div>
</body>
</html>