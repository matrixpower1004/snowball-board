package com.snowball.board.domain.user.service;

import com.snowball.board.common.exception.message.ExceptionMessage;
import com.snowball.board.domain.user.dto.*;
import com.snowball.board.domain.user.model.User;
import com.snowball.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String validateDuplicateNickName(ValidateNickNameDuplicateRequest validateNickNameDuplicateRequest) {
        validateDuplicateNickName(validateNickNameDuplicateRequest.getNickName());

        return "사용 가능";
    }

    public String validateDuplicateEmail(ValidateEmailDuplicateRequest validateEmailDuplicateRequest) {
        validateDuplicateEmail(validateEmailDuplicateRequest.getEmail());

        return "사용 가능";
    }

    private void validateDuplicateNickName(String nickName) {
        Optional<User> findUser = userRepository.findByNickName(nickName);
        if (findUser.isPresent()) {
            throw new IllegalStateException(ExceptionMessage.DUPLICATE_NICKNAME.message());
        }
    }

    // TODO: 2023-07-20 email 
    private void validateDuplicateEmail(String email) {
        Optional<User> findUser = userRepository.findByNickName(email);
        if (findUser.isPresent()) {
            throw new IllegalStateException(ExceptionMessage.DUPLICATE_EMAIL.message());
        }
    }

    public GetInfoResponse getUserInfo(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new IllegalStateException(ExceptionMessage.USER_NOT_FOUND.message());
        }

        User user = findUser.get();
        return GetInfoResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .userRole(user.getUserRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
    // Update -> @Transactional(readOnly = false)
    @Transactional
    public UpdateInfoResponse updateUserInfo(Long id, UpdateInfoRequest updateInfoRequest) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new IllegalStateException(ExceptionMessage.USER_NOT_FOUND.message());
        }
        User user = findUser.get();
        if (updateInfoRequest.getEmail() != null) {
            String email = updateInfoRequest.getEmail();
            validateDuplicateEmail(email);
            user.updateEmail(email);
        }

        if (updateInfoRequest.getNickName() != null) {
            String nickName = updateInfoRequest.getNickName();
            validateDuplicateEmail(nickName);
            user.updateNickName(nickName);
        }

        return UpdateInfoResponse.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }

    @Transactional
    public String updatePassword(Long id, UpdatePasswordRequest updatePasswordRequest) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new IllegalStateException(ExceptionMessage.USER_NOT_FOUND.message());
        }
        User user = findUser.get();
        user.updatePassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

        return "비밀번호가 성공적으로 수정되었습니다.";
    }

}
