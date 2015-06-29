<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/view/common/taglibs.jsp" %>

<div class="comment_feed">
	<textarea name="commentContent" style="width:360px; height:33px;"></textarea>
	<input type="button" value="评论" onclick="comment('${blogId}', this);" style="vertical-align:bottom;" disabled="disabled" />
</div>

<div class="comment_list">
	<c:forEach var="comment" items="${commentList}">
	<div class="list_li">
		<div>${comment.username}：${comment.content}</div>
		<div>${comment.createTime}</div>
	</div>
	</c:forEach>
</div>

<c:if test="${commentCount > 0}">
<div class="center">
	<a href="javascript:;" onclick="toBlogDetail('${blogId}');">还有${commentCount}条评论，查看更多>></a>
</div>
</c:if>


