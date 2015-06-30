<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/view/common/taglibs.jsp" %>

<div>
<c:forEach var="blog" items="${blogPage.result}">
	<div style="margin:5px 0;background-color:#Fff;">
		<div>
			<span style="float:left;">
				<c:if test="${blog.username==null}">游客</c:if>
				<c:if test="${blog.username!=null}">
					<a href="javascript:toUserIndex('${blog.userId}');">${blog.username}</a>
				</c:if>
			</span>
			<span style="float:right;">${blog.createTime }</span>
			<div style="clear: both;"></div>
		</div>
		<div>${blog.content }</div>
		<div>
			<ul class="wb_feed_handle clearfix">
				<li>
				
					<a href="javascript:;" onclick="loadComment('${blog._id}',this);" data-opend="0">
						评论 <span id="commentCount${blog._id}">${blog.commentList.size()>0 ? blog.commentList.size() : ''}</span>
					</a>
				
				</li>
				<li>
					<c:set var="agreed" value="${blog.agreeList.contains(currentUser._id) }" />
					<a href="javascript:;" onclick="agree('${blog._id}',this);" data-agreed="${agreed}" class="${agreed ? 'agreed' : 'agree'}">
						赞 <span id="agreeCount${blog._id}">${blog.agreeList.size()>0 ? blog.agreeList.size() : ''}</span>
					</a>
					
				</li>
			</ul>
		</div>
		
		<div class="comment" id="comment${blog._id}">
		</div>
		
	</div>
</c:forEach>
</div>

<div style="text-align: center;">
	<c:if test="${blogPage.totalPages > blogPage.page}">
		<a href="javascript:;" onclick="loadMore(${blogPage.page + 1});">加载更多</a>
	</c:if>
</div>