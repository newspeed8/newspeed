package com.example.newspeed.user.service;

import com.example.newspeed.common.config.PasswordEncoder;
import com.example.newspeed.user.dto.request.UserPasswordUpdateRequestDto;
import com.example.newspeed.user.dto.request.UserSaveRequestDto;
import com.example.newspeed.user.dto.response.UserResponseDto;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto save(UserSaveRequestDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        //요청받은 데이터 저장
        User user = new User(dto.getUserName(), dto.getEmail(), encodedPassword);
        userRepository.save(user);
        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user->new UserResponseDto(
                        user.getId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getPassword()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto findOne(Long id) {
        User user= userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword());
    }
// --------- 프로필 서비스로 이동 ----------

//    @Transactional
//    public UserResponseDto updatePassword(Long id, String oldPassword, String newPassword) {
//        User user=userRepository.findById(id)
//                .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
//
//        //기존 비밀번호를 틀리게 작성할 경우 에러 발생
//        if(!passwordEncoder.matches(oldPassword,user.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"기존 비밀번호가 일치하지 않습니다.");
//        }
//
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        //변경된 비밀번호로 저장
//        user.updatePassword(encodedPassword);
//
//        return new UserResponseDto(
//                user.getId(),
//                user.getUserName(),
//                user.getEmail(),
//                user.getPassword());
//    }
//
//    @Transactional
//    public UserResponseDto updateUserName(Long id, String newUserName) {
//        User user=userRepository.findById(id)
//                .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
//
//        //변경된 유저 이름 저장
//        user.updateUserName(newUserName);
//
//        return new UserResponseDto(
//                user.getId(),
//                user.getUserName(),
//                user.getEmail(),
//                user.getPassword());
//    }

//    @Transactional
//    public void deleteUserById(Long id) {
//        userRepository.deleteById(id);
//    }
}
