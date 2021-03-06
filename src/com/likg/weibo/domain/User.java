package com.likg.weibo.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User {
	
	private String _id;

	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 注册时间
	 */
	private String createTime;
	
	/**
	 * 关注的用户列表(userId)
	 */
	private Set<String> followList = new HashSet<String>();

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Set<String> getFollowList() {
		return followList;
	}

	public void setFollowList(Set<String> followList) {
		this.followList = followList;
	}
	
}
