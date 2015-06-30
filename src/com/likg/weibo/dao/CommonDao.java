package com.likg.weibo.dao;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDao {
	@Resource
	private MongoTemplate mongoTemplate;
	
	/**
	 * 获取文档的总记录数
	 * @param clazz 文档类型
	 * @return
	 * @author likaige
	 * @create 2015年6月29日 下午4:58:30
	 */
	public long getTotalCount(Class<?> clazz) {
		return mongoTemplate.count(null, clazz);
	}
	
	/**
	 * 新增文档对象
	 * @param obj 文档对象
	 * @author likaige
	 * @create 2015年6月29日 下午5:32:35
	 */
	public void saveObj(Object obj) {
		mongoTemplate.insert(obj);
	}
	
	/**
	 * 根据id获取文档对象
	 * @param id 文档id
	 * @param clazz 文档类型
	 * @return
	 * @author likaige
	 * @create 2015年6月29日 下午5:41:21
	 */
	public <T> T getObjById(String id, Class<T> clazz) {
		return mongoTemplate.findById(id, clazz);
	}
	
}
