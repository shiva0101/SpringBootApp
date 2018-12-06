package com.app.serviceImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.model.User;
import com.app.service.UserService;
import com.app.userRepoImpl.CreateUserImpl;
import com.app.userRepository.UserRepository;

/**
 * This class just interact with our custom repository for generic CRUD
 * operations for a specific type.
 * 
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

	int flag = 1;
	/**
	 * @Autowired annotation Marks a field, as to be autowired by Spring's
	 *            dependency injection facilities
	 */
	@Autowired
	UserRepository userRepository;

	@Autowired
	CreateUserImpl createUserImpl;

	/**
	 * Created the Logger Object to get logging info
	 */
	final static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Override
	public User createUser(User user) {

		return userRepository.save(user);
	}

	public List<User> getUser() {

		/*
		 * MyThread myThread = applicationContext.getBean(MyThread.class);
		 * taskExecutor.execute(myThread);
		 */
		return (List<User>) userRepository.findAll();
	}

	public User findById(long id) {

		return userRepository.findOne(id);
	}

	public User update(User user, long l) {

		return userRepository.save(user);
	}

	public void deleteUserById(long id) {

		userRepository.delete(id);
	}

	@Override
	public Page<User> getAllPaginatedUsers(Long start, Long size) {

		logger.info("Entering in getAllPaginatedUsers");
		Integer start1 = (int) (long) start;
		Integer size1 = (int) (long) size;
		return userRepository.findAll(new PageRequest(start1, size1));
	}

}