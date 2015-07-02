<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/view/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>微博首页</title>
<%@ include file="/view/common/head.jsp" %>

<style type="text/css">
ol, ul {
    list-style: outside none none;
}

.wb_feed_handle {
	border-top-style: solid;
	border-top-width: 1px;
	border-color: #f2f2f5;
}
.wb_feed_handle li{
	float:left;
	width:50%;
	height:26px;
	position: relative;
}
.wb_feed_handle a{
	text-align:center;
	display: block;
}

.agree{
	color:#666666;
}
.agreed{
	color:#eb7350;
}

</style>
<script type="text/javascript">
$(function(){
	
	$('textarea[name=commentContent]').keyup(function(dom){
		var content = $(this).val();
		var disabled = ($.trim(content) == '');
		$('#submitBut').prop('disabled', disabled);
	});
	
	loadComment(1, '?pager.offset=0');
	
});

function comment(blogId, dom){
	var content = $(dom).parent().find('textarea').val();
	$.post(sysPath + '/indexController/comment.do', {blogId:blogId, content:content}, function(json){
		if(json == ''){
			location.href = sysPath + '/login.jsp';
		}else{
			location.href = location.href;
		}
	});
}

function loadComment(page,pageUrl){
	var pageUrl = '${path}/indexController/loadCommentPage.do'+pageUrl;
	pageUrl += '&blogId=${blog._id}&page=' + page;
	$('#dataListDiv').html('数据加载中...').load(pageUrl);
}



</script>
</head>
<body style="">

<!-- 固定导航 -->
<%@ include file="/view/common/top.jsp" %>


<div id="container" style="">


<div style="margin: 10px 0;">
	<div>${blog.content}</div>
</div>

<div class="clearfix">
	<textarea name="commentContent" style="width:360px; height:33px;"></textarea>
	<input type="button" id="submitBut" value="评论" onclick="comment('${blog._id}', this);" style="vertical-align:bottom;" disabled="disabled" />
	<span>${commentCount}</span>
</div>

<div id="dataListDiv">
</div>

</div>
</body>
</html>