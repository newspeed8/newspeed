package com.example.newspeed.user.service;

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

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(UserLoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new InvalidCredentialException("이메일이 존재하지 않습니다.")
        );

        String password = dto.getPassword();
        if (password == null) {
            throw new InvalidCredentialException("비밀번호를 입력해주세요.");
        }

        if (!password.equals(user.getPassword())) {
            throw new InvalidCredentialException("비밀번호가 틀렸습니다.");
        }
        return new UserLoginResponseDto(user.getId());
    }

}
