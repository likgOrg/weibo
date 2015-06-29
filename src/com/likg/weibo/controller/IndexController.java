package com.likg.weibo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.likg.weibo.domain.Comment;
import com.likg.weibo.domain.MicroBlog;
import com.likg.weibo.domain.Page;
import com.likg.weibo.domain.User;
import com.likg.weibo.service.MicroBlogService;

@Controller
@RequestMapping("indexController")
public class IndexController extends BaseController {
	
	private static Log log = LogFactory.getLog(IndexController.class);
	
	@Resource
	private MicroBlogService microBlogService;
	
	@RequestMapping("toIndexView")
	public ModelAndView toIndexView(){
		Map<String, Object> model = new HashMap<String, Object>();
		
		log.info("index info...");
		log.debug("index debug...");
		
		long totalCount = microBlogService.getTotalCount(MicroBlog.class);
		model.put("totalBlogCount", totalCount);
		
		long totalUserCount = microBlogService.getTotalCount(User.class);
		model.put("totalUserCount", totalUserCount);
		
		return new ModelAndView("index", model);
	}
	
	@RequestMapping("loadMicroBlog")
	public ModelAndView loadMicroBlog(Page<MicroBlog> page){
		Map<String, Object> model = new HashMap<String, Object>();
		
		Page<MicroBlog> blogPage = microBlogService.getMicroBlogPage(page);
		model.put("blogPage", blogPage);
		
		return new ModelAndView("view/blogList", model);
	}
	
	@RequestMapping("saveMicroBlog")
	public ModelAndView saveMicroBlog(MicroBlog blog){
		User user = this.getCurrentUser();
		if(user != null){
			blog.setUsername(user.getUsername());
		}
		microBlogService.saveMicroBlog(blog);
		
		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/");
	}
	
	@ResponseBody
	@RequestMapping("regist")
	public Map<String, Object> regist(HttpServletRequest request, String username){
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		
		User user = microBlogService.getUser(username);
		if(user == null){
			user = microBlogService.regist(username);
			this.setCurrentUser(user);
		}else{
			result = "用户名已存在！";
		}
		
		map.put("result", result);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("login")
	public Map<String, Object> login(HttpServletRequest request, String username){
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
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request, MicroBlog blog){
		request.getSession().invalidate();
		
		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/");
	}
	
	
	@RequestMapping("toUserList")
	public ModelAndView toUserList(){
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<User> userList = microBlogService.getUserList();
		model.put("userList", userList);
		
		return new ModelAndView("userList", model);
	}
	
	@RequestMapping("toUserIndex")
	public ModelAndView toUserIndex(String username){
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<MicroBlog> blogList = microBlogService.getMicroBlogList(username);
		model.put("blogList", blogList);
		model.put("username", username);
		
		long blogCount = microBlogService.getMicroBlogCount(username);
		model.put("blogCount", blogCount);
		
		long followCount = microBlogService.getFollowCount(username);
		model.put("followCount", followCount);
		
		long fansCount = microBlogService.getFansCount(username);
		model.put("fansCount", fansCount);
		
		//判断该用户我是否关注了
		User user = this.getCurrentUser();
		if(user != null){
			if(user.getFollowList().contains(username)){
				model.put("followed", true);
			}
		}
		
		return new ModelAndView("view/userIndex", model);
	}
	
	@ResponseBody
	@RequestMapping("agree")
	public long agree(String blogId, Boolean agreed){
		//未登录
		User user = this.getCurrentUser();
		if(user == null){
			return -1;
		}
		
		long count = microBlogService.agree(user.get_id(), blogId, agreed);
		return count;
	}
	
	@ResponseBody
	@RequestMapping("comment")
	public Map<String, Object> comment(String blogId, String content){
		Map<String, Object> result = new HashMap<String, Object>();
		
		//未登录
		User user = this.getCurrentUser();
		if(user == null){
			return null;
		}
		
		Comment comment = microBlogService.comment(user.getUsername(), blogId, content);
		result.put("comment", comment);
		
		long commentCount = microBlogService.getCommentCount(blogId);
		result.put("commentCount", commentCount);
		
		return result;
	}
	
	
	@RequestMapping("loadComment")
	public ModelAndView loadComment(String blogId){
		Map<String, Object> model = new HashMap<String, Object>();
		
		Page<Comment> page = new Page<Comment>(1, 5);
		Page<Comment> commentPage = microBlogService.getCommentPage(blogId, page);
		model.put("commentList", commentPage.getResult());
		model.put("blogId", blogId);
		
		long commentCount = microBlogService.getCommentCount(blogId);
		model.put("commentCount", commentCount-commentPage.getResult().size());
		
		return new ModelAndView("view/commentList", model);
	}
	
	@RequestMapping("loadCommentPage")
	public ModelAndView loadCommentPage(String blogId, Page<Comment> page){
		Map<String, Object> model = new HashMap<String, Object>();
		
		System.out.println("11111111111111111");
		
		Page<Comment> commentPage = microBlogService.getCommentPage(blogId, page);
		model.put("commentList", commentPage.getResult());
		model.put("blogId", blogId);
		model.put("totalCount", commentPage.getTotal());
		//model.put("limit", commentPage.getLimit());
		
		//long commentCount = microBlogService.getCommentCount(blogId);
		model.put("commentCount", commentPage.getTotal()-commentPage.getResult().size());
		
		return new ModelAndView("view/commentPage", model);
	}
	
	@RequestMapping("toBlogDetail")
	public ModelAndView toBlogDetail(String blogId){
		Map<String, Object> model = new HashMap<String, Object>();
		
		MicroBlog blog = microBlogService.getMicroBlog(blogId);
		model.put("blog", blog);
		
		return new ModelAndView("view/blogDetail", model);
	}
	
	@ResponseBody
	@RequestMapping("follow")
	public long follow(String username){
		//未登录
		User user = this.getCurrentUser();
		if(user == null){
			return -1;
		}
		
		long count = microBlogService.follow(user.get_id(), username);
		
		this.refreshCurrentUser();
		
		return count;
	}

	private void refreshCurrentUser() {
		User user = microBlogService.getUser(this.getCurrentUser().getUsername());
		this.setCurrentUser(user);
	}
	
}
