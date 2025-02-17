package com.example.newspeed.profile.service;

import com.example.newspeed.common.config.PasswordEncoder;
import com.example.newspeed.post.dto.response.PostResponse;
import com.example.newspeed.post.service.PostService;
import com.example.newspeed.user.dto.response.UserResponseDto;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;


    @Transactional
    public UserResponseDto updatePassword(Long id, String oldPassword, String newPassword) {
        User user=userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        //기존 비밀번호를 틀리게 작성할 경우 에러 발생
        if(!passwordEncoder.matches(oldPassword,user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"기존 비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        //변경된 비밀번호로 저장
        user.updatePassword(encodedPassword);

        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    @Transactional
    public UserResponseDto updateUserName(Long id, String newUserName) {
        User user=userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        //변경된 유저 이름 저장
        user.updateUserName(newUserName);

        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    @Transactional
    public List<PostResponse> getUserPosts(Long userId){
        return postService.getPostByUserId(userId);
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
