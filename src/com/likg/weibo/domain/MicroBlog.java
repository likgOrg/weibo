package com.likg.weibo.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="blog")
public class MicroBlog {
	
	private String _id;

	private String username;
	
	private String content;
	
	private String createTime;
	
	private Set<String> agreeList = new HashSet<String>();
	
	private List<Comment> commentList = new ArrayList<>();

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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
