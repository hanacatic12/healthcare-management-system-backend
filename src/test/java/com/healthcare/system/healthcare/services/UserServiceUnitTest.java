package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.UserDto;
import com.healthcare.system.healthcare.models.entities.User;
import com.healthcare.system.healthcare.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setUid(1);
        mockUser.setName("John Doe");
        mockUser.setEmail("john@example.com");
        mockUser.setPassword("password");
        mockUser.setPhone("123456789");
        mockUser.setAddress("123 Street");
        mockUser.setCity("City");
        mockUser.setDob(LocalDate.of(1990, 1, 1));
        mockUser.setGender("Male");
        mockUser.setBlood_group("O+");
        mockUser.setRole("patient");
        mockUser.setJmbg("1234567890123");
    }

    @Test
    public void testGetUser_existingUser_returnsDto() {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        UserDto result = userService.getUser(1);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        verify(userRepository).findById(1);
    }

    @Test
    public void testGetUser_nonExistingUser_returnsNull() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        UserDto result = userService.getUser(99);

        assertThat(result).isNull();
        verify(userRepository).findById(99);
    }

    @Test
    public void testUpdateUser_existingUser_updatesAndReturnsDto() {
        UserDto updateDto = new UserDto(1, null, "newemail@example.com", null, "111222333", "New Address", "New City",
                LocalDate.of(1995, 5, 5), "Female", "A+", null, null);

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto result = userService.updateUser(1, updateDto);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("newemail@example.com");
        assertThat(result.getPhone()).isEqualTo("111222333");
        assertThat(result.getAddress()).isEqualTo("New Address");
        assertThat(result.getCity()).isEqualTo("New City");
        assertThat(result.getDob()).isEqualTo(LocalDate.of(1995, 5, 5));
        assertThat(result.getGender()).isEqualTo("Female");
        assertThat(result.getBlood_group()).isEqualTo("A+");

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testUpdateUser_partialFields_updatesOnlyProvided() {
        UserDto updateDto = new UserDto(1, null, "updated@example.com", null, null, null, null, null, null, null, null, null);

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserDto result = userService.updateUser(1, updateDto);

        assertThat(result.getEmail()).isEqualTo("updated@example.com");
        assertThat(result.getPhone()).isEqualTo(mockUser.getPhone()); // unchanged
    }


    @Test
    public void testUpdateUser_nonExistingUser_returnsNull() {
        UserDto updateDto = new UserDto(99, null, "new@example.com", null, "999", "Nowhere", "Ghost City",
                LocalDate.of(2000, 1, 1), "Other", "AB-", null, null);

        when(userRepository.findById(99)).thenReturn(Optional.empty());

        UserDto result = userService.updateUser(99, updateDto);

        assertThat(result).isNull();
        verify(userRepository).findById(99);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testUpdateUser_nullDto_returnsNull() {
        UserDto result = userService.updateUser(1, null);
        assertThat(result).isNull();
    }
}
