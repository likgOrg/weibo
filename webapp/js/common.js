


/**
 * 关注
 * @param username 用户名
 */
function toBlogDetail(blogId){
	//var followed = $(dom).data('followed');
	location.href = sysPath + '/indexController/toBlogDetail.do?blogId='+blogId;
}

/**
 * 关注
 * @param username 用户名
 */
function follow(username, dom){
	var followed = $(dom).data('followed');
	//alert(followed)
	$.post(sysPath + '/indexController/follow.do', {username:username}, function(json){
		if(json == -1){
			location.href = sysPath + '/login.jsp';
		}else{
			$(dom).data('followed', !followed);
			$(dom).html(followed ? '关注' : '取消关注');
		}
	});
}


/**
 * 加载评论列表
 * @param blogId 微博id
 */
function loadComment(blogId, dom){
	var opend = $(dom).data('opend');
	//alert(opend)
	if(opend){
		$('#comment'+blogId).html('');
	}else{
		$('#comment'+blogId).load(sysPath + '/indexController/loadComment.do', {blogId:blogId}, function(json){
			$('#comment'+blogId).find('textarea').keyup(function(dom){
				var content = $(this).val();
				var disabled = ($.trim(content) == '');
				$(this).parent().find('input').prop('disabled', disabled);
			});
		});
	}
	$(dom).data('opend', !opend);
}
/**
 * 评论
 * @param blogId 微博id
 */
function comment(blogId, dom){
	var content = $(dom).parent().find('textarea').val();
	//alert(agreed)
	$.post(sysPath + '/indexController/comment.do', {blogId:blogId, content:content}, function(json){
		//alert(json=='')
		if(json == ''){
			location.href = sysPath + '/login.jsp';
		}else{
			var html = '<div class="list_li">';
			html += '<div>'+json.comment.username+'：'+json.comment.content+'</div>';
			html += '<div>'+json.comment.createTime+'</div>';
			html += '</div>';
			
			$('#comment'+blogId).find('div:eq(1)').prepend(html);
			$(dom).parent().find('textarea').val('');
			$(dom).parent().find('input').prop('disabled', true);
			$('#commentCount'+blogId).html(json.commentCount);
		}
	});
}
/**
 * 赞
 * @param blogId 微博id
 */
function agree(blogId, dom){
	var agreed = $(dom).data('agreed');
	//alert(agreed)
	$.post(sysPath + '/indexController/agree.do', {blogId:blogId, agreed:agreed}, function(json){
		if(json == -1){
			location.href = sysPath + '/login.jsp';
		}else{
			$('#agreeCount'+blogId).html(json);
			$(dom).data('agreed', !agreed);
			$('#agreeCount'+blogId).parent().removeClass().addClass(agreed ? 'agree' : 'agreed');
		}
	});
}

/**
 * 跳转到粉丝列表页面
 * @param username 用户名
 */
function toFansList(username){
	//location.href = sysPath + '/indexController/toUserIndex.do?username='+username;
	
	var form = $('<form method="post"></form>');
	$(form).prop('action', sysPath + '/indexController/toFansList.do');
	$(form).append('<input name="username" value="'+username+'" />');
	$(form).appendTo($('body'));
	$(form).submit();
}

/**
 * 跳转到关注列表页面
 * @param username 用户名
 */
function toFollowList(username){
	//location.href = sysPath + '/indexController/toUserIndex.do?username='+username;
	
	var form = $('<form method="post"></form>');
	$(form).prop('action', sysPath + '/indexController/toFollowList.do');
	$(form).append('<input name="username" value="'+username+'" />');
	$(form).appendTo($('body'));
	$(form).submit();
}

/**
 * 跳转到个人中心页面
 * @param userId 用户ID
 */
function toUserIndex(userId){
	location.href = sysPath + '/indexController/toUserIndex.do?userId='+userId;
	
	/*
	var form = $('<form method="post"></form>');
	$(form).prop('action', sysPath + '/indexController/toUserIndex.do');
	$(form).append('<input name="username" value="'+username+'" />');
	$(form).appendTo($('body'));
	$(form).submit();
	*/
}

/**
 * 格式化日期，输出格式：yyyy-MM-dd HH:mm:ss
 * @param ms 毫秒
 */
function formatDate(ms){
	var d = new Date(ms);
	return d.getFullYear()+ '-' +(d.getMonth()+1)+ '-' +d.getDate()+ ' ' +d.getHours()+ ':' +d.getMinutes()+ ':' +d.getSeconds();
}