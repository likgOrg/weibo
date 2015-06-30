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


	public Page<MicroBlog> getMicroBlogPage(Page<MicroBlog> page) {
		
		Query query = new Query();
		Sort sort = new Sort(Direction.DESC, "_id");
		query.with(sort);
		
		
		query.skip(page.getIndex()).limit(page.getLimit());
		
		
		List<MicroBlog> list = mongoTemplate.find(query, MicroBlog.class);
		
		long count = mongoTemplate.count(null, MicroBlog.class);
		System.out.println(count);
		
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

	public List<MicroBlog> getMicroBlogList(String username) {
		//List<MicroBlog> list = mongoTemplate.findAll(MicroBlog.class, "blog");
		Query query = new Query();
		Sort sort = new Sort(Direction.DESC, "_id");
		query.with(sort);
		query.addCriteria(Criteria.where("username").is(username));
		
		List<MicroBlog> list = mongoTemplate.find(query, MicroBlog.class);
		
		long count = mongoTemplate.count(query, MicroBlog.class);
		System.out.println(count);
		
		return list;
	}

	public long getMicroBlogCount(String username) {
		Query query = new Query(Criteria.where("username").is(username));
		long count = mongoTemplate.count(query, MicroBlog.class);
		return count;
	}
	
	public long getFollowCount(String username) {
		Query query = new Query(Criteria.where("username").is(username));
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

	public void follow(String currUserId, String username) {
		Query query = new Query(Criteria.where("_id").is(currUserId));
		User user = mongoTemplate.findOne(query, User.class);
		
		Update update = new Update();
		if(user.getFollowList().contains(username)){
			update.pull("followList", username);
		}else{
			update.push("followList", username);
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
	
	public long getFansCount(String username) {
		Criteria c = Criteria.where("followList").is(username);
		Query query = new Query(c);
		
		long count = mongoTemplate.count(query, User.class);
		System.out.println("fansCount=="+count);
		return count;
	}

	public List<User> getFansList(String username) {
		Query query = new Query(Criteria.where("followList").is(username));
		Sort sort = new Sort(Direction.DESC, "_id");
		query.with(sort);
		
		
		List<User> list = mongoTemplate.find(query, User.class);
		
		return list;
	}

	
	
	
	
	
	
	
}
