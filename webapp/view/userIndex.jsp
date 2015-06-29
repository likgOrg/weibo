<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/view/common/taglibs.jsp" %>
<html>
<head>
<title>个人空间</title>
<%@ include file="/view/common/head.jsp" %>

<script type="text/javascript">
$(function(){
	$('#content').keyup(function(dom){
		var content = $(this).val();
		if($.trim(content) == ''){
			$('#submitBut').prop('disabled', true);
		}else{
			$('#submitBut').prop('disabled', false);
		}
		console.log('a'+$.trim(content)+"b")
	});
	
});

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
<body>

<!-- 固定导航 -->
<%@ include file="/view/common/top.jsp" %>

<div id="container">



<div class="clearfix">
	<div style="float:left;">
		<span style="font-weight:bold;">${username}的个人空间</span>&nbsp;&nbsp;
		<c:if test="${currentUser!=null && currentUser.username != username}">
			<a href="javascript:;" onclick="follow('${username}',this);" data-followed="${followed}">${followed ? '取消关注' : '关注'}</a>
		</c:if>
	</div>
	<div style="float:right;">
		<span>关注:${followCount }</span>&nbsp;&nbsp;
		<span>粉丝:${fansCount }</span>&nbsp;&nbsp;
		<span>微博:${blogCount }</span>
	</div>
</div>

<div>
	<hr/>
	<c:forEach var="blog" items="${blogList}">
		<div>
			<span style="float: left;">${blog.username==null ? '游客' : blog.username }</span>
			<span style="float: right;">${blog.createTime }</span>
			<div style="clear: both;"></div>
		</div>
		<div>${blog.content }</div>
		<hr/>
	</c:forEach>

</div>



</div>
</body>
</html>