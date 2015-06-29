package com.likg.framework;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.likg.weibo.domain.User;

public class BaseController {

	@Resource
	private HttpSession session;
	
	/**
	 * 获取当前用户对象
	 * @return
	 * @author likaige
	 * @create 2015年6月15日 下午4:11:37
	 */
	public User getCurrentUser(){
		return (User)session.getAttribute(Constant.CURRENT_USER_KEY);
	}
	
	
	public void setCurrentUser(User user){
		session.setAttribute(Constant.CURRENT_USER_KEY, user);
	}
}
