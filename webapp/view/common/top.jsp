<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/view/common/taglibs.jsp" %>
<div class="header">
<div class="gn_header clearfix">
	<div class="gn_position">
		<a href="${path}/">首页</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="${path}/indexController/toUserList.do">找人</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		<c:if test="${currentUser == null}">
			<a href="${path}/register.jsp">注册</a> |
			<a href="${path}/login.jsp">登录</a>
		</c:if>
		<c:if test="${currentUser != null}">
			<a href="javascript:toUserIndex('${currentUser.username}');">${currentUser.username }</a> |
			<a href="${path}/loginController/logout.do">退出</a>
		</c:if>
	</div>
</div>
</div>