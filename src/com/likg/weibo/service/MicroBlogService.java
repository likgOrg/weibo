package com.likg.weibo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.likg.weibo.dao.CommonDao;
import com.likg.weibo.dao.MicroBlogDao;
import com.likg.weibo.domain.Comment;
import com.likg.weibo.domain.MicroBlog;
import com.likg.weibo.domain.Page;
import com.likg.weibo.domain.User;
import com.likg.weibo.util.DateUtil;

@Service
public class MicroBlogService {
	
	@Resource
	private CommonDao commonDao;
	
	@Resource
	private MicroBlogDao microBlogDao;

	/**
	 * 发布微博
	 * @param blog 微博对象
	 * @param user 发布者信息
	 * @author likaige
	 * @create 2015年6月30日 上午11:26:43
	 */
	public void saveMicroBlog(MicroBlog blog, User user) {
		if(user != null){
			blog.setUserId(user.get_id());
			blog.setUsername(user.getUsername());
		}
		blog.setCreateTime(DateUtil.getNowTime());
		commonDao.saveObj(blog);
	}

	public User regist(String username) {
		User user = new User();
		user.setCreateTime(DateUtil.getNowTime());
		user.setUsername(username);
		commonDao.saveObj(user);
		
		user = microBlogDao.getUser(username);
		return user;
	}
	
	public User getUser(String username) {
		return microBlogDao.getUser(username);
	}

	/**
	 * 分页获取微博数据
	 * @param page 分页信息
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 上午11:20:48
	 */
	public Page<MicroBlog> getMicroBlogPage(Page<MicroBlog> page) {
		return microBlogDao.getMicroBlogPage(page);
	}
	
	public Page<Comment> getCommentPage(String blogId, Page<Comment> page) {
		return microBlogDao.getCommentPage(blogId, page);
	}

	public List<User> getUserList() {
		return microBlogDao.getUserList();
	}

	/**
	 * 获取指定用户的微博列表数据
	 * @param userId 用户id
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 上午11:39:43
	 */
	public List<MicroBlog> getMicroBlogList(String userId) {
		return microBlogDao.getMicroBlogList(userId);
	}

	/**
	 * 获取用户发布的总微博数量
	 * @param userId
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:23:36
	 */
	public long getMicroBlogCount(String userId) {
		return microBlogDao.getMicroBlogCount(userId);
	}
	
	/**
	 * 获取用户的关注人数
	 * @param userId
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:24:29
	 */
	public long getFollowCount(String userId) {
		return microBlogDao.getFollowCount(userId);
	}

	/**
	 * 获取文档的总记录数
	 * @param clazz 文档类型
	 * @return
	 * @author likaige
	 * @create 2015年6月29日 下午4:58:30
	 */
	public long getTotalCount(Class<?> clazz) {
		return commonDao.getTotalCount(clazz);
	}

	public long agree(String userId, String blogId, Boolean agreed) {
		//取消赞
		if(agreed){
			microBlogDao.cancleAgree(userId, blogId);
		}else{
			microBlogDao.agree(userId, blogId);
		}
		
		long agreeCount = microBlogDao.getAgreeCount(blogId);
		
		return agreeCount;
	}

	public List<Comment> getCommentList(String blogId) {
		return microBlogDao.getCommentList(blogId);
	}

	public Comment comment(String username, String blogId, String content) {
		return microBlogDao.comment(username, blogId, content);
	}

	public long getCommentCount(String blogId) {
		return microBlogDao.getCommentCount(blogId);
	}

	public long follow(String currUserId, String userId) {
		microBlogDao.follow(currUserId, userId);
		return 0;
	}

	/**
	 * 获取用户的粉丝人数
	 * @param userId
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:25:05
	 */
	public long getFansCount(String userId) {
		return microBlogDao.getFansCount(userId);
	}

	public MicroBlog getMicroBlog(String blogId) {
		return commonDao.getObjById(blogId, MicroBlog.class);
	}

	public List<User> getFansList(String username) {
		return microBlogDao.getFansList(username);
	}

	public List<User> getFollowList(String username) {
		
		User user = microBlogDao.getUser(username);
		
		
		
		
		return microBlogDao.getUserList(user.getFollowList());
	}

	public User getUserById(String userId) {
		return commonDao.getObjById(userId, User.class);
	}

}
