import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class Teste1 {

	private UserRepository userRepository;
	private User user;

	@BeforeEach
	public void init() {
		userRepository = mock(UserRepository.class);
		user = new User("usuario teste", "teste@gmail.com");
		Mockito.when(userRepository.getUserById(1)).thenReturn(user);
	}

	@Test
	@DisplayName("verifica se usuário é válido com um nome e email válidos")
	public void verifyUserIsValid_ValidNameAndEmail_ReturnsTrue() {
		UserService service = new UserService(userRepository);
		Assertions.assertTrue(service.verifyUserIsValid(1));
	}

	@Test
	@DisplayName("verifica se usuário é válido com um nome inválido")
	public void verifyUserIsValid_InvalidName_ReturnsFalse() {
		user.Name = "a";

		UserService service = new UserService(userRepository);
		Assertions.assertFalse(service.verifyUserIsValid(1));
	}

	@Test
	@DisplayName("verifica se usuário é válido com um email inválido")
	public void verifyUserIsValid_InvalidEmail_ReturnsFalse() {
		user.Email = "a";

		UserService service = new UserService(userRepository);
		Assertions.assertFalse(service.verifyUserIsValid(1));
	}
}

class User {
	public User(String name, String email) {
		this.Name = name;
		this.Email = email;
	}

	public String Name;
	public String Email;
}

interface UserRepository {
	User getUserById(int id);
}

class UserService {
	private final UserRepository _userRepository;

	public UserService(UserRepository userRepository) {
		_userRepository = userRepository;
	}

	public boolean verifyUserIsValid(int id) {
		User user = _userRepository.getUserById(id);
		if (user.Name == null || user.Name.length() < 2 || user.Name.length() > 80)
			return false;
		if (user.Email == null || user.Email.length() < 5 || user.Email.length() > 200)
			return false;
		return true;
	}
}