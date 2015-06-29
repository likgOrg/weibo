package com.likg.weibo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.likg.weibo.dao.MicroBlogDao;
import com.likg.weibo.domain.Comment;
import com.likg.weibo.domain.MicroBlog;
import com.likg.weibo.domain.Page;
import com.likg.weibo.domain.User;
import com.likg.weibo.util.DateUtil;

@Service
public class MicroBlogService {
	
	@Resource
	private MicroBlogDao microBlogDao;

	public void saveMicroBlog(MicroBlog blog) {
		
		blog.setCreateTime(DateUtil.getNowTime());
		microBlogDao.saveMicroBlog(blog);
	}


	public User regist(String username) {
		User user = new User();
		user.setCreateTime(DateUtil.getNowTime());
		user.setUsername(username);
		microBlogDao.saveUser(user);
		
		user = microBlogDao.getUser(username);
		return user;
	}
	
	public User getUser(String username) {
		return microBlogDao.getUser(username);
	}

	public Page<MicroBlog> getMicroBlogPage(Page<MicroBlog> page) {
		return microBlogDao.getMicroBlogPage(page);
	}
	
	public Page<Comment> getCommentPage(String blogId, Page<Comment> page) {
		return microBlogDao.getCommentPage(blogId, page);
	}

	public List<User> getUserList() {
		return microBlogDao.getUserList();
	}

	public List<MicroBlog> getMicroBlogList(String username) {
		return microBlogDao.getMicroBlogList(username);
	}

	public long getMicroBlogCount(String username) {
		return microBlogDao.getMicroBlogCount(username);
	}
	
	public long getFollowCount(String username) {
		return microBlogDao.getFollowCount(username);
	}

	public long getTotalCount(Class<?> class1) {
		return microBlogDao.getTotalCount(class1);
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

	public long follow(String currUserId, String username) {
		microBlogDao.follow(currUserId, username);
		return 0;
	}

	public long getFansCount(String username) {
		return microBlogDao.getFansCount(username);
	}

	public MicroBlog getMicroBlog(String blogId) {
		return microBlogDao.getMicroBlog(blogId);
	}

	

}
