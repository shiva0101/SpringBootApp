package com.app.userRepoImpl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.model.User;
import com.app.userRepository.CreateUserRepo;
import com.app.userRepository.UserDeptRepo;

/**
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018
 */

@Repository
public class CreateUserImpl implements CreateUserRepo {

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Override
	public User createUser(User user) {

		EntityManager em = entityManagerFactory.createEntityManager();
		em.merge(user);
		
		return user;
	}

}