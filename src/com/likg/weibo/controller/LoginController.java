package com.likg.weibo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.likg.framework.BaseController;
import com.likg.weibo.domain.User;
import com.likg.weibo.service.MicroBlogService;

@Controller
@RequestMapping("loginController")
public class LoginController extends BaseController {
	
	private static Log log = LogFactory.getLog(LoginController.class);
	
	private static ReentrantLock lock = new ReentrantLock();

	
	@Resource
	private MicroBlogService microBlogService;
	
	/**
	 * 注册
	 * @param username 用户名
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:27:18
	 */
	@ResponseBody
	@RequestMapping("regist")
	public Map<String, Object> regist(String username){
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		
		try {
			lock.lock();
			User user = microBlogService.getUser(username);
			if(user == null){
				user = microBlogService.regist(username);
				this.setCurrentUser(user);
			}else{
				result = "用户名已存在！";
			}
			lock.unlock();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			if(lock.isLocked()){
				lock.unlock();
			}
		}
		
		map.put("result", result);
		return map;
	}
	
	/**
	 * 登录
	 * @param username
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:30:43
	 */
	@ResponseBody
	@RequestMapping("login")
	public Map<String, Object> login(String username){
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		
		User user = microBlogService.getUser(username);
		if(user != null){
			this.setCurrentUser(user);
		}else{
			result = "用户名不存在！";
		}
		
		map.put("result", result);
		return map;
	}
	
	/**
	 * 注销
	 * @param request
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:31:16
	 */
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request){
		request.getSession().invalidate();
		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/");
	}
	
}
