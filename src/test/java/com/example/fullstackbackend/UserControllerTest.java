package com.example.fullstackbackend;
import com.example.fullstackbackend.controller.UserController;
import com.example.fullstackbackend.exception.UserNotFoundException;
import com.example.fullstackbackend.model.User;
import com.example.fullstackbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserController userController = new UserController();

    public UserControllerTest() {
        userController.setUserRepository(userRepository);
    }

    @Test
    void testGetAllUsers() {
        User u1 = new User(1L, "Иван", "ivan", "ivan@example.com");
        User u2 = new User(2L, "Анна", "anna", "anna@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        var result = userController.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByIdFound() {
        User u = new User(1L, "Иван", "ivan", "ivan@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        var result = userController.getUserById(1L);

        assertEquals("ivan", result.getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userController.getUserById(99L));
    }

    @Test
    void testCreateUser() {
        User input = new User(null, "Иван", "ivan", "ivan@example.com");
        User saved = new User(1L, "Иван", "ivan", "ivan@example.com");

        when(userRepository.save(input)).thenReturn(saved);

        var result = userController.newUser(input);

        assertEquals(1L, result.getId());
        verify(userRepository).save(input);
    }

    @Test
    void testDeleteUserSuccess() {
        when(userRepository.existsById(1L)).thenReturn(true);

        var result = userController.deleteUser(1L);

        assertEquals("User with id 1 has been deleted success.", result);
        verify(userRepository).deleteById(1L);
    }

}
