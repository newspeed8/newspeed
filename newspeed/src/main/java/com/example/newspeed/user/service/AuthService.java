package com.example.newspeed.user.service;

import com.example.newspeed.common.config.PasswordEncoder;
import com.example.newspeed.exception.InvalidCredentialException;
import com.example.newspeed.user.dto.request.UserLoginRequestDto;
import com.example.newspeed.user.dto.response.UserLoginResponseDto;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User login(UserLoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialException("이메일이 존재하지 않습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("비밀번호가 틀렸습니다.");
        }
        return user;
    }
}
