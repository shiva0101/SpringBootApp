package com.app.userTestCases;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.app.model.User;
import com.app.serviceImpl.UserServiceImpl;
import com.app.userRepository.UserRepository;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
class UserTestCases {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userService;

	public static Collection input() {
		User u1 = new User(1, "india", "shiva");
		User u2 = new User(2, "USA", "Rohan");
		User u3 = new User(3, "india", "Vivek");

		return Arrays.asList(new Object[][] { { new User(1L, "india", "shiva"), u1 },
				{ new User(2, "USA", "Rohan"), u2 }, { new User(3, "india", "Vivek"), u3 } });

	}

	@ParameterizedTest
	@MethodSource("input")
	void getUserByIdtest(User user, User expectedUser) {
		when(userRepository.findOne(user.getId())).thenReturn(user);
		User result = userService.findById(user.getId());
		assertEquals(result.getCountry(), expectedUser.getCountry());
		assertEquals(result.getName(), expectedUser.getName());
	}

	@ParameterizedTest
	@MethodSource("input")
	public void testDeleteUser(User user, User expectedUser) {

		userService.deleteUserById(user.getId());
		verify(userRepository, times(1)).delete(expectedUser.getId());

	}

	@ParameterizedTest
	@MethodSource("input")
	public void testUpdateUser(User user, User expectedUser) {

		when(userRepository.save(user)).thenReturn(user);
		User result = userService.update(user, user.getId());

		assertEquals(user.getId(), result.getId());
		assertEquals(user.getCountry(), result.getCountry());
		assertEquals(user.getName(), result.getName());
	}

}