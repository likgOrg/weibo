<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/view/common/taglibs.jsp" %>


<div class="comment_list">
	<c:forEach var="comment" items="${commentList}">
	<div class="list_li">
		<div>${comment.username}：${comment.content}</div>
		<div>${comment.createTime}</div>
	</div>
	</c:forEach>
</div>



<pg:pager url="" items="${totalCount}" export="currentPageNumber=pageNumber" maxPageItems="5">
<pg:index>
    <pg:first unless="current">  
        <a href="javascript:;" onclick="loadComment(${pageNumber }, '${pageUrl}');">首页</a>  
    </pg:first>  
    <pg:prev>  
        <a href="javascript:;" onclick="loadComment(${pageNumber }, '${pageUrl}');">前页</a>  
    </pg:prev>  
    <pg:pages>  
        <c:choose>  
            <c:when test="${currentPageNumber eq pageNumber}">  
                <font color="red">${pageNumber }</font>  
            </c:when>  
            <c:otherwise>   
                <a href="javascript:;" onclick="loadComment(${pageNumber }, '${pageUrl}');">${pageNumber }</a>  
            </c:otherwise>  
        </c:choose>  
    </pg:pages>  
    <pg:next>  
        <a href="javascript:;" onclick="loadComment(${pageNumber }, '${pageUrl}');">后页</a>  
    </pg:next>  
    <pg:last unless="current">  
        <a href="javascript:;" onclick="loadComment(${pageNumber }, '${pageUrl}');">尾页</a>  
    </pg:last>
</pg:index>
</pg:pager>


