<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/view/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>微博首页</title>
<%@ include file="/view/common/head.jsp" %>
<style type="text/css">
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
	$('#content').keyup(function(dom){
		var content = $(this).val();
		var disabled = ($.trim(content) == '');
		$('#submitBut').prop('disabled', disabled);
	});
	
	//加载微博列表
	loadMore(1);
});

//加载微博列表
function loadMore(page){
	var url = '${path}/indexController/loadMicroBlog.do';
	$('#tempData').load(url, {page:page}, function(){
		var data = $('#tempData').find('div').eq(0);
		$('#blogData').append(data.html());
		data.html('');
	});
}

</script>
</head>
<body style="background-color:#B4DAF0;">

<!-- 固定导航 -->
<%@ include file="/view/common/top.jsp" %>


<div id="container" style="background-color:#93C5E2;">

<div class="clearfix" style="height:62px;">
	<form action="${path}/indexController/saveMicroBlog.do" method="post" style="float:left;">
		<textarea id="content" name="content" style="width:300px; height:60px;"></textarea>
		<input id="submitBut" type="submit" value="发布" disabled="disabled" style="height:38px;width:80px;vertical-align:bottom;" />
	</form>
	
	<div style="float:right; margin-top:35px;">
		会员:${totalUserCount}&nbsp;&nbsp;
		微博:${totalBlogCount}
	</div>
</div>

<div id="blogData"></div>
<div id="tempData"></div>

</div>
</body>
</html>