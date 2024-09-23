package com.BackendProject.Shopeeee.service.user;

import com.BackendProject.Shopeeee.dto.UserDto;
import com.BackendProject.Shopeeee.exception.AlreadyExistsException;
import com.BackendProject.Shopeeee.exception.ResourceNotFoundException;
import com.BackendProject.Shopeeee.model.User;
import com.BackendProject.Shopeeee.repository.UserRepository;
import com.BackendProject.Shopeeee.request.CreateUserRequest;
import com.BackendProject.Shopeeee.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).filter( user -> !userRepository.existsByEmail(request.getEmail())).map(req -> {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            return userRepository.save(user);
        }).orElseThrow(()-> new AlreadyExistsException("ooopppss"+request.getEmail()+"already exist!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {

        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("user not found"));

    }

    @Override
    public void deleteUser(Long userId) {
          userRepository.findById(userId).ifPresentOrElse(userRepository :: delete,
                  ()->{throw  new ResourceNotFoundException("user not found");}
          );
    }

    @Override
    public UserDto convertUserToDto(User user){
       return modelMapper.map(user,UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();;

        return userRepository.findByEmail(email);
    }
}
