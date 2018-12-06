package com.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018
 */

@Entity
public class User_Dept {

	@Id
	private long user_id;

	private long dept_id;

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getDept_id() {
		return dept_id;
	}

	public void setDept_id(long dept_id) {
		this.dept_id = dept_id;
	}

}
