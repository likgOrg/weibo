package com.likg.weibo.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="blogList")
public class Comment {
	
	private String _id;

	private String username;
	
	private String content;
	
	private String createTime;
	
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

}
