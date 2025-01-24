package com.georgi.whatsappclone.service;

import com.georgi.whatsappclone.configuration.mapper.UserMapper;
import com.georgi.whatsappclone.model.entity.UserResponse;
import com.georgi.whatsappclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public List<UserResponse> getAllUsersExceptSelf(Authentication connectedUser) {
       return userRepository.findAllUsersExceptSelf(connectedUser.getName())
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

}
