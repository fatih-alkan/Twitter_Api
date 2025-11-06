package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.user.UserPatchRequestDto;
import com.fatihalkan.twitter_api.dto.user.UserRequestDto;
import com.fatihalkan.twitter_api.dto.user.UserResponseDto;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.UserNotFoundException;
import com.fatihalkan.twitter_api.mapper.UserMapper;
import com.fatihalkan.twitter_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<UserResponseDto> getAll() {

        return repository.findAll().stream().map(mapper::toResponseDto).toList();
    }

    @Override
    public UserResponseDto getById(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isPresent()){
            return mapper.toResponseDto(optionalUser.get());
        }
        throw new UserNotFoundException("User not found id: " + id);
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        if (repository.findByUsername(userRequestDto.userName()).isPresent()) {
            throw new RuntimeException("Username already exists: " + userRequestDto.userName());
        }
        if (repository.findByEmail(userRequestDto.email()).isPresent()) {
            throw new RuntimeException("Email already registered: " + userRequestDto.email());
        }
        User user = mapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.toResponseDto(repository.save(user));
    }


    @Override
    public UserResponseDto replaceOrCreate(Long id, UserRequestDto userRequestDto) {
        Optional<User> optionalUser = repository.findById(id);

        User userToReplaceOrCreate = mapper.toEntity(userRequestDto);
        userToReplaceOrCreate.setPassword(passwordEncoder.encode(userToReplaceOrCreate.getPassword()));
        if (optionalUser.isPresent()){
            userToReplaceOrCreate.setId(id);
            repository.save(userToReplaceOrCreate);
            return mapper.toResponseDto(userToReplaceOrCreate);
        }
        return mapper.toResponseDto(repository.save(userToReplaceOrCreate));
    }

    @Override
    public UserResponseDto update(Long id, UserPatchRequestDto userPatchRequestDto) {
        User userToUpdate = repository.
                findById(id).
                orElseThrow(()-> new UserNotFoundException("User not found id: " + id));
        mapper.updateEntity(userToUpdate,userPatchRequestDto);
        if (userPatchRequestDto.password() != null && !userPatchRequestDto.password().isBlank()) {
            userToUpdate.setPassword(passwordEncoder.encode(userPatchRequestDto.password()));
        }
        return mapper.toResponseDto(repository.save(userToUpdate));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found username: " + username));
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found id: " + id));
    }


}
