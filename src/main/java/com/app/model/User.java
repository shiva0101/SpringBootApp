package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This is an Entity class
 * 
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018
 */

@Entity
@Table(name = "UserInfo")
public class User {

	/**
	 * @Id to mark the field as primary key
	 * @ @GeneratedValue(strategy=GenerationType.AUTO) This is the default
	 *   GenerationType, i.e. if we just use @GeneratedValue annotation then this
	 *   value of GenerationType will be used.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Min(value = 1, message = "User Id should not negative")
	private long id;

	/**
	 * @Column annotation Specifies the mapped column for a persistent property or
	 *         field.
	 */
	@NotEmpty(message = "Country can't be Empty")
	@Column(name = "country")
	private String country;

	@NotEmpty(message = "Name can't be Empty")
	@Column(name = "name")
	private String name;

	/**
	 * This is an Default constructor
	 */
	public User() {
		super();
	}

	/**
	 * Returns a string representation of the object. This method is overrided from
	 * Object class
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", country=" + country + ", name=" + name + ", getId()=" + getId() + ", getName()="
				+ getName() + ", getCountry()=" + getCountry() + "]";
	}

	/**
	 * This constructor a User with a specified id, name and country
	 * 
	 * @param id      of the user
	 * @param country of the user
	 * @param name    of the user
	 */

	public User(long id, String country, String name) {
		this.id = id;
		this.name = name;
		this.country = country;
	}

	/**
	 * This returns the current id of this user
	 * 
	 * @return this user's id
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * This returns the current name of this user
	 * 
	 * @return this user's name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This returns the current country of this user
	 * 
	 * @return this user's country
	 */
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
