package com.fatihalkan.twitter_api.mapper;

import com.fatihalkan.twitter_api.dto.user.UserPatchRequestDto;
import com.fatihalkan.twitter_api.dto.user.UserRequestDto;
import com.fatihalkan.twitter_api.dto.user.UserResponseDto;
import com.fatihalkan.twitter_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequestDto userRequestDto){
        User user = new User();
        user.setUserName(userRequestDto.userName());
        user.setFirstName(userRequestDto.firstName());
        user.setLastName(userRequestDto.lastName());
        user.setEmail(userRequestDto.email());
        user.setPhoneNumber(userRequestDto.phoneNumber());
        user.setPassword(userRequestDto.password());

        return user;
    }

    public UserResponseDto toResponseDto(User user){
        return new UserResponseDto(
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
    public void updateEntity(User userToUpdate, UserPatchRequestDto userPatchRequestDto){
        if (userPatchRequestDto.userName() != null){
            userToUpdate.setUserName(userPatchRequestDto.userName());
        }
        if(userPatchRequestDto.firstName() !=null){
            userToUpdate.setFirstName(userPatchRequestDto.firstName());
        }
        if(userPatchRequestDto.lastName() != null){
            userToUpdate.setLastName(userPatchRequestDto.lastName());
        }
        if (userPatchRequestDto.email() != null){
            userToUpdate.setEmail(userPatchRequestDto.email());
        }
        if(userPatchRequestDto.phoneNumber() != null){
            userToUpdate.setPhoneNumber(userPatchRequestDto.phoneNumber());
        }
        if (userPatchRequestDto.password() != null){
            userToUpdate.setPassword(userPatchRequestDto.password());
        }
    }
}
