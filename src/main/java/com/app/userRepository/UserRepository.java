package com.app.userRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.User;

/**
 * This is our custom repository extends CrudRepository for using some inbuilt
 * methods
 * 
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Iterable<User> findAll(Sort sort);

	Page<User> findAll(Pageable pageable);
}