package com.BackendProject.Shopeeee.service.user;

import com.BackendProject.Shopeeee.dto.UserDto;
import com.BackendProject.Shopeeee.model.User;
import com.BackendProject.Shopeeee.request.CreateUserRequest;
import com.BackendProject.Shopeeee.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
