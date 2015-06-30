package com.likg.weibo.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 微博
 * @author likaige
 * @create 2015年6月30日 上午10:49:54
 */
@Document(collection="blog")
public class MicroBlog {
	
	private String _id;

	/**
	 * 发布人的id
	 */
	private String userId;
	
	/**
	 * 发布人的用户名
	 */
	private String username;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 点赞用户列表(userId)
	 */
	private Set<String> agreeList = new HashSet<String>();
	
	/**
	 * 评论列表
	 */
	private List<Comment> commentList = new ArrayList<>();

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Set<String> getAgreeList() {
		return agreeList;
	}

	public void setAgreeList(Set<String> agreeList) {
		this.agreeList = agreeList;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
}
