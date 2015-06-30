package com.likg.weibo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(IndexController.class);
	
	@Resource
	private MicroBlogService microBlogService;
	
	/**
	 * 跳转到‘首页’
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 上午11:12:32
	 */
	@RequestMapping("toIndexView")
	public ModelAndView toIndexView(){
		Map<String, Object> model = new HashMap<String, Object>();
		
		//获取微博总记录数
		long totalCount = microBlogService.getTotalCount(MicroBlog.class);
		model.put("totalBlogCount", totalCount);
		
		//获取会员总记录数
		long totalUserCount = microBlogService.getTotalCount(User.class);
		model.put("totalUserCount", totalUserCount);
		
		return new ModelAndView("index", model);
	}
	
	/**
	 * 分页加载微博数据
	 * @param page 分页信息
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 上午11:17:10
	 */
	@RequestMapping("loadMicroBlog")
	public ModelAndView loadMicroBlog(Page<MicroBlog> page){
		Map<String, Object> model = new HashMap<String, Object>();
		
		//分页获取微博数据
		Page<MicroBlog> blogPage = microBlogService.getMicroBlogPage(page);
		model.put("blogPage", blogPage);
		
		return new ModelAndView("view/blogList", model);
	}
	
	/**
	 * 发布微博
	 * @param blog 微博对象
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 上午11:25:37
	 */
	@RequestMapping("saveMicroBlog")
	public ModelAndView saveMicroBlog(MicroBlog blog){
		User user = this.getCurrentUser();
		microBlogService.saveMicroBlog(blog, user);
		return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/");
	}
	
	/**
	 * 找人
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:45:22
	 */
	@RequestMapping("toUserList")
	public ModelAndView toUserList(){
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<User> userList = microBlogService.getUserList();
		model.put("userList", userList);
		
		return new ModelAndView("view/userList", model);
	}
	
	/**
	 * 跳转到‘用户中心’页面
	 * @param userId 用户ID
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 上午11:32:22
	 */
	@RequestMapping("toUserIndex")
	public ModelAndView toUserIndex(String userId){
		Map<String, Object> model = new HashMap<String, Object>();
		
		//获取用户信息
		User user = microBlogService.getUserById(userId);
		model.put("username", user.getUsername());
		
		//获取微博列表数据
		List<MicroBlog> blogList = microBlogService.getMicroBlogList(userId);
		model.put("blogList", blogList);
		
		//获取用户发布的总微博数量
		long blogCount = microBlogService.getMicroBlogCount(userId);
		model.put("blogCount", blogCount);
		
		//获取用户的关注人数
		long followCount = microBlogService.getFollowCount(userId);
		model.put("followCount", followCount);
		
		//获取用户的粉丝人数
		long fansCount = microBlogService.getFansCount(userId);
		model.put("fansCount", fansCount);
		
		//判断该用户我是否关注了
		User currentUser = this.getCurrentUser();
		if(currentUser != null){
			if(currentUser.getFollowList().contains(userId)){
				model.put("followed", true);
			}
		}
		
		return new ModelAndView("view/userIndex", model);
	}
	
	/**
	 * 赞
	 * @param blogId
	 * @param agreed
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:33:57
	 */
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
	
	/**
	 * 评论
	 * @param blogId
	 * @param content
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:45:48
	 */
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
		
		Page<Comment> commentPage = microBlogService.getCommentPage(blogId, page);
		model.put("commentList", commentPage.getResult());
		model.put("blogId", blogId);
		model.put("totalCount", commentPage.getTotal());
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
	
	/**
	 * 关注
	 * @param userId
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:34:53
	 */
	@ResponseBody
	@RequestMapping("follow")
	public long follow(String userId){
		//未登录
		User user = this.getCurrentUser();
		if(user == null){
			return -1;
		}
		
		long count = microBlogService.follow(user.get_id(), userId);
		
		this.refreshCurrentUser();
		
		return count;
	}

	private void refreshCurrentUser() {
		User user = microBlogService.getUser(this.getCurrentUser().getUsername());
		this.setCurrentUser(user);
	}
	
	
	@RequestMapping("toFansList")
	public ModelAndView toFansList(String username){
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<User> userList = microBlogService.getFansList(username);
		model.put("userList", userList);
		model.put("username", username);
		
		return new ModelAndView("view/fansList", model);
	}
	
	@RequestMapping("toFollowList")
	public ModelAndView toFollowList(String username){
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<User> userList = microBlogService.getFollowList(username);
		model.put("userList", userList);
		model.put("username", username);
		
		return new ModelAndView("view/followList", model);
	}
	
}
