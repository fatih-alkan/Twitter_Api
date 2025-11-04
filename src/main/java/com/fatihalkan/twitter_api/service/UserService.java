package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.user.UserPatchRequestDto;
import com.fatihalkan.twitter_api.dto.user.UserRequestDto;
import com.fatihalkan.twitter_api.dto.user.UserResponseDto;
import com.fatihalkan.twitter_api.entity.User;

import java.util.List;

public interface UserService {
    //GET
    List<UserResponseDto> getAll();
    UserResponseDto getById(Long id);
    //POST
    UserResponseDto create(UserRequestDto userRequestDto);
    //PUT
    UserResponseDto replaceOrCreate(Long id, UserRequestDto userRequestDto);
    //PATCH
    UserResponseDto update(Long id, UserPatchRequestDto userPatchRequestDto);
    //DELETE
    void delete(Long id);

    User findByUsername(String username);

    User findById(Long id);
}
