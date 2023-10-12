package com.avangenio.warehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.avangenio.warehouse.model.User;
import com.avangenio.warehouse.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void createUserTest() {
		long count;// = userRepository.count();
		
		String username = "angel";
		
		User testUser = userRepository.findByUsername(username);
		
		if(Objects.nonNull(testUser)) {
			
			userRepository.delete(testUser);
			
		} 
		
		count = userRepository.count();
		
		User newUser = createUser(username, "password", "Angel Test", "+5354761153", new String[] {"ROLE_USER", "ROLE_ADMIN"});
		
		assertNotNull(newUser);
		
		assertNotNull(newUser.getId());
		
		long countAfter = userRepository.count();
		
		assertEquals(count+1, countAfter);
	}
	
	private User createUser(String username, String password, String fullname, String phoneNumber, String[] roles) {
		
		User user = User.builder()
				.username(username)
				.password(password)
				.fullname(fullname)
				.phoneNumber(phoneNumber)
				.roles(roles)
				.build();
		
		user = userRepository.save(user);

		return user;
	}
}
