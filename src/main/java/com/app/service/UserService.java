package com.app.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.app.model.User;
import com.app.model.User_Dept;

/**
 * Interface for generic CRUD operations for a specific type.
 * 
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018
 */

public interface UserService {

	public User createUser(User user);

	public List<User> getUser();

	public User findById(long id);

	public User update(User user, long l);

	public void deleteUserById(long id);

	public Page<User> getAllPaginatedUsers(Long start, Long size);



}