package com.app.userRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.User_Dept;



public interface UserDeptRepo extends JpaRepository<User_Dept, Long>{

	
	
	
	List<User_Dept> findAll(long myId);

	
}
