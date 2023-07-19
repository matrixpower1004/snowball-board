package com.snowball.board.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowball.board.common.exception.message.ExceptionMessage;
import com.snowball.board.common.util.ApiResponse;
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

    public ApiResponse validateDuplicateNickName(ValidateNickNameDuplicateRequest validateNickNameDuplicateRequest) {
        validateDuplicateNickName(validateNickNameDuplicateRequest.getNickName());

        return ApiResponse.builder()
                .message("사용 가능")
                .build();
    }

    public ApiResponse validateDuplicateEmail(ValidateEmailDuplicateRequest validateEmailDuplicateRequest) {
        validateDuplicateEmail(validateEmailDuplicateRequest.getEmail());

        return ApiResponse.builder()
                .message("사용 가능")
                .build();
    }

    private void validateDuplicateNickName(String nickName) {
        Optional<User> findUser = userRepository.findByNickName(nickName);
        if (findUser.isPresent()) {
            throw new IllegalStateException(ExceptionMessage.DUPLICATE_NICKNAME.message());
        }
    }

    private void validateDuplicateEmail(String email) {
        Optional<User> findUser = userRepository.findByNickName(email);
        if (findUser.isPresent()) {
            throw new IllegalStateException(ExceptionMessage.DUPLICATE_EMAIL.message());
        }
    }

    public ApiResponse getUserInfo(Long id) throws JsonProcessingException {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new IllegalStateException(ExceptionMessage.USER_NOT_FOUND.message());
        }

        User user = findUser.get();
        GetInfoResponse getInfoResponse = GetInfoResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .userRole(user.getUserRole())
                .createdAt(user.getCreatedAt())
                .build();

        return ApiResponse.builder()
                .data(new ObjectMapper().writeValueAsString(getInfoResponse))
                .build();
    }
    // Update -> @Transactional(readOnly = false)
    @Transactional
    public ApiResponse updateUserInfo(Long id, UpdateInfoRequest updateInfoRequest) throws JsonProcessingException {
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

        UpdateInfoResponse updateInfoResponse = UpdateInfoResponse.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();

        return ApiResponse.builder()
                .data(new ObjectMapper().writeValueAsString(updateInfoResponse))
                .build();
    }

    @Transactional
    public ApiResponse updatePassword(Long id, UpdatePasswordRequest updatePasswordRequest) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new IllegalStateException(ExceptionMessage.USER_NOT_FOUND.message());
        }
        User user = findUser.get();
        user.updatePassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

        return ApiResponse.builder()
                .message("비밀번호가 성공적으로 수정되었습니다.")
                .build();
    }
}
