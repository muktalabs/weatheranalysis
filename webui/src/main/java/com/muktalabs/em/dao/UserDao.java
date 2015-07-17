package com.muktalabs.em.dao;

import java.util.List;

import com.muktalabs.em.model.User;

public interface UserDao {

	public int saveOrUpdate(User user);

	public List<User> list();
	
	public List<User> list(User criteria);
	
	public List<User> list(String filterColumnName, String filterColumnValue);

	public User getById(int id);

	public int delete(int id);
	
	public User getUserByUsername(String username);
	
}