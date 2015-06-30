package com.likg.weibo.dao;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.likg.weibo.domain.Comment;
import com.likg.weibo.domain.MicroBlog;
import com.likg.weibo.domain.Page;
import com.likg.weibo.domain.User;
import com.likg.weibo.util.DateUtil;

@Repository
public class MicroBlogDao {

	@Resource
	private MongoTemplate mongoTemplate;


	public User getUser(String username) {
		Query query = new Query(Criteria.where("username").is(username));
		User User = mongoTemplate.findOne(query, User.class);
		return User;
	}
	
	
	public List<User> getUserList(Set<String> usernameList) {
		Query query = new Query(Criteria.where("username").in(usernameList));
		return mongoTemplate.find(query, User.class);
	}

	/**
	 * 分页获取微博数据
	 * @param page 分页信息
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 上午11:20:48
	 */
	public Page<MicroBlog> getMicroBlogPage(Page<MicroBlog> page) {
		Query query = new Query();
		query.with(new Sort(Direction.DESC, "_id"));
		query.skip(page.getIndex()).limit(page.getLimit());
		
		List<MicroBlog> list = mongoTemplate.find(query, MicroBlog.class);
		long count = mongoTemplate.count(null, MicroBlog.class);
		
		page.setResult(list);
		page.setTotal(count);
		
		return page;
	}

	public List<User> getUserList() {
		Query query = new Query();
		Sort sort = new Sort(Direction.DESC, "_id");
		query.with(sort);
		
		
		List<User> list = mongoTemplate.find(query, User.class);
		
		long count = mongoTemplate.count(null, User.class);
		System.out.println(count);
		
		return list;
	}

	/**
	 * 获取指定用户的微博列表数据
	 * @param userId 用户id
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 上午11:39:43
	 */
	public List<MicroBlog> getMicroBlogList(String userId) {
		Query query = new Query();
		query.with(new Sort(Direction.DESC, "_id"));
		query.addCriteria(Criteria.where("userId").is(userId));
		
		List<MicroBlog> list = mongoTemplate.find(query, MicroBlog.class);
		return list;
	}

	/**
	 * 获取用户发布的总微博数量
	 * @param userId
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:23:45
	 */
	public long getMicroBlogCount(String userId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		long count = mongoTemplate.count(query, MicroBlog.class);
		return count;
	}
	
	/**
	 * 获取用户的关注人数
	 * @param userId
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:24:40
	 */
	public long getFollowCount(String userId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		User user = mongoTemplate.findOne(query, User.class);
		return user.getFollowList().size();
	}

	

	public void agree(String userId, String blogId) {
		Query query = new Query(Criteria.where("_id").is(blogId));
		//MicroBlog blog = mongoTemplate.findOne(query, MicroBlog.class);
		
		Update update = new Update();
		update.push("agreeList", userId);
		
		mongoTemplate.upsert(query, update, MicroBlog.class);
	}
	
	public void cancleAgree(String userId, String blogId) {
		Query query = new Query(Criteria.where("_id").is(blogId));
		//MicroBlog blog = mongoTemplate.findOne(query, MicroBlog.class);
		
		Update update = new Update();
		update.pull("agreeList", userId);
		
		mongoTemplate.upsert(query, update, MicroBlog.class);
	}

	public long getAgreeCount(String blogId) {
		Query query = new Query(Criteria.where("_id").is(blogId));
		MicroBlog blog = mongoTemplate.findOne(query, MicroBlog.class);
		
		
		return blog.getAgreeList().size();
	}

	public List<Comment> getCommentList(String blogId) {
		Query query = new Query(Criteria.where("_id").is(blogId));
		MicroBlog blog = mongoTemplate.findOne(query, MicroBlog.class);
		return blog.getCommentList();
	}

	public Comment comment(String username, String blogId, String content) {
		Query query = new Query(Criteria.where("_id").is(blogId));
		
		Comment Comment = new Comment();
		Comment.setContent(content);
		Comment.setUsername(username);
		Comment.setCreateTime(DateUtil.getNowTime());
		
		Update update = new Update();
		update.push("commentList", Comment);
		
		mongoTemplate.upsert(query, update, MicroBlog.class);
		
		return Comment;
	}

	public long getCommentCount(String blogId) {
		Query query = new Query(Criteria.where("_id").is(blogId));
		MicroBlog blog = mongoTemplate.findOne(query, MicroBlog.class);
		
		//long count = mongoTemplate.count(query, MicroBlog.class);
		//mongoTemplate.getCollection("blog").count(query)
		
		//1
		
		return blog.getCommentList().size();
	}

	/**
	 * 关注
	 * @param currUserId
	 * @param userId
	 * @author likaige
	 * @create 2015年6月30日 下午1:36:23
	 */
	public void follow(String currUserId, String userId) {
		Query query = new Query(Criteria.where("_id").is(currUserId));
		User user = mongoTemplate.findOne(query, User.class);
		
		Update update = new Update();
		if(user.getFollowList().contains(userId)){
			update.pull("followList", userId);
		}else{
			update.push("followList", userId);
		}
		
		mongoTemplate.upsert(query, update, User.class);
	}

	public Page<Comment> getCommentPage(String blogId, Page<Comment> page) {
		
		Query query = new Query(Criteria.where("_id").is(blogId));
		MicroBlog blog = mongoTemplate.findOne(query, MicroBlog.class);
		
		if(blog.getCommentList().isEmpty()){
			return page;
		}
		
		List<Comment> list = null;
		int toIndex = page.getIndex()+page.getLimit();
		if(toIndex >= blog.getCommentList().size()){
			toIndex = blog.getCommentList().size();
		}
		list = blog.getCommentList().subList(page.getIndex(), toIndex);
		
		page.setResult(list);
		page.setTotal(blog.getCommentList().size());
		
		return page;
	}
	
	/**
	 * 获取用户的粉丝人数
	 * @param userId
	 * @return
	 * @author likaige
	 * @create 2015年6月30日 下午1:25:13
	 */
	public long getFansCount(String userId) {
		Query query = new Query(Criteria.where("followList").is(userId));
		long count = mongoTemplate.count(query, User.class);
		return count;
	}

	public List<User> getFansList(String username) {
		Query query = new Query(Criteria.where("followList").is(username));
		query.with(new Sort(Direction.DESC, "_id"));
		List<User> list = mongoTemplate.find(query, User.class);
		return list;
	}

}
