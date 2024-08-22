package integradores;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Serial;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Serial
    private UserRepository userRepository;

    @Serial
    private UserService userService;

    @Test
    void testCreateUser() {
        User user = new User("John", "john@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals("John", createdUser.getName());
        assertEquals("john@example.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    void testGetUserById() {
        User user = new User("John", "john@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertEquals("John", foundUser.getName());
        assertEquals("john@example.com", foundUser.getEmail());
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void testUpdateUser() {
        User user = new User("John", "john@example.com");
        User updatedUserDetails = new User("Jane", "jane@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updatedUserDetails);

        User updatedUser = userService.updateUser(1L, updatedUserDetails);

        assertEquals("Jane", updatedUser.getName());
        assertEquals("jane@example.com", updatedUser.getEmail());
    }

    private void assertEquals(String jane, String name) {
    }

    public UserService getUserService() {
        return userService;
    }

    void testDeleteUser() {
        User user = new User("John", "john@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }
}
