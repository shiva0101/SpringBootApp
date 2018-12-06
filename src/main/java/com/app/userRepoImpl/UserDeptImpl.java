/*package com.app.userRepoImpl;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.controller.UserController;
import com.app.model.User_Dept;
import com.app.userRepository.UserDeptRepo;

@Repository
public class UserDeptImpl implements UserDeptRepo {

	final static Logger logger = Logger.getLogger(UserDeptImpl.class);
	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Override
	public List<User_Dept> findAll(long myId) {

		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = em.createQuery("SELECT u FROM User_Dept u WHERE u.user_id = :userId");
		query.setParameter("userId", myId);
		List<User_Dept> resultList = query.getResultList();
		resultList.forEach(System.out::println);
		logger.info("resultList " + resultList);
		Iterator<User_Dept> itr = resultList.iterator();
		while (itr.hasNext()) {
			User_Dept uds = itr.next();
			logger.info(uds.getDept_id());
		}
		em.close();
		return resultList;
	}

}
*/