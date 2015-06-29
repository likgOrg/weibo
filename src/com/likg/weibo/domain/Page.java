package com.likg.weibo.domain;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

	/**
	 * 当前页
	 */
	private int page = 1;
	
	/**
	 * 每页数据量
	 */
	private int limit = 5;

	/**
	 * 总记录数
	 */
	private long total;
	
	/**
	 * 列表数据
	 */
	private List<T> result = new ArrayList<T>();
	
	public Page(){}
	
	public Page(int page, int limit){
		this.page = page;
		this.limit = limit;
	}
	
	/**
	 * 获取检索起始位置
	 */
	public int getIndex() {
		return (this.page-1) * this.limit;
	}
	
	/**
	 * 计算总页数
	 */
	public long getTotalPages() {
		return (this.total-1)/this.limit + 1;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
}
