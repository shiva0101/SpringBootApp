package com.app.serviceImplTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.app.model.User;
import com.app.serviceImpl.UserServiceImpl;
import com.app.userRepoImpl.CreateUserImpl;
import com.app.userRepository.UserRepository;

/**
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018 This is Test class for dao and service layers
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
 
	@Mock
	UserRepository userRepository;

	@Mock
	CreateUserImpl userRepository1;

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	EntityManager entityManager;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test for create User
	 */
	@Test
	public void testCreateUser() {

		User user = new User(8, "india", "ram");
		when(userRepository.save(user)).thenReturn(user);
		User result = userService.createUser(user);
		assertEquals(8, result.getId());
		assertEquals("india", result.getCountry());
		assertEquals("ram", result.getName());
	}

	/**
	 * Test for getAllUser
	 */
	@Test
	public void testGetAllUser() {

		List<User> userlist = new ArrayList<>();
		userlist.add(new User(1, "India", "Shiva"));
		userlist.add(new User(2, "USA", "Rohan"));
		userlist.add(new User(3, "India", "Shishir"));
		when(userRepository.findAll()).thenReturn(userlist);

		List<User> result = userService.getUser();
		assertEquals(3, result.size());
	}

	/**
	 * Test for getUserById
	 */
	@Test
	public void testGetUserById() {

		User user = new User(10L, "India", "Shiva");
		user.setId(10L);

		when(userRepository.findOne(10L)).thenReturn(user);
		User result = userService.findById(user.getId());
		assertEquals("India", result.getCountry());
		assertEquals("Shiva", result.getName());
	}

	/**
	 * Test for deleteUser
	 */
	@Test
	public void testDeleteUser() {

		User user = new User(10, "India", "Rohan");
		userService.deleteUserById(10L);
		verify(userRepository, times(1)).delete(user.getId());

	}

}
