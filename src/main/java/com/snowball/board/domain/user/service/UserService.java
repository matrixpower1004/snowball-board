package com.snowball.board.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snowball.board.common.util.ApiResponse;
import com.snowball.board.common.util.JsonObjectMapper;
import com.snowball.board.domain.auth.dto.AuthenticationResponse;
import com.snowball.board.domain.auth.service.AuthenticationService;
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
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    /**
     * Register User
     * @param request
     * @return user id(Auto generated)
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .userAccount(request.getUserAccount())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .nickName(request.getNickName())
                .build();
        validateUserAccountDuplicate(user.getUserAccount());
        userRepository.save(user);
        String jwtToken = authenticationService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Validate Duplicate UserAccount(Login ID) exists
     * @param userAccount
     * throws Exception
     */
    private void validateUserAccountDuplicate(String userAccount){
        Optional<User> findUser = userRepository.findByUserAccount(userAccount);
        if (findUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
    }

    public ApiResponse validateDuplicateNickName(ValidateNickNameDuplicateRequest validateNickNameDuplicateRequest) {
        Optional<User> findUser = userRepository.findByNickName(validateNickNameDuplicateRequest.getNickName());
        if (findUser.isPresent()) {
            throw new IllegalStateException("해당 사용자 닉네임은 이미 사용 중입니다.");
        }

        return ApiResponse.builder()
                .message("사용 가능")
                .build();
    }

    public ApiResponse getUserInfo(Long id) throws JsonProcessingException {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new IllegalStateException("Not found");
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
                .data(JsonObjectMapper.objectToJson(getInfoResponse))
                .build();
    }

    // TODO: 2023-07-17 modify to update email or nickname are optional, email validation
    @Transactional
    public ApiResponse updateUserInfo(Long id, UpdateInfoRequest updateInfoRequest) throws JsonProcessingException {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new IllegalStateException("not fount");
        }
        User user = findUser.get();
        if (user.getNickName().equals(updateInfoRequest.getNickName())) {
            throw new IllegalStateException("해당 사용자 닉네임은 이미 사용 중입니다.");
        }
        user.updateEmail(updateInfoRequest.getEmail());
        user.updateNickName(updateInfoRequest.getNickName());

        UpdateInfoResponse updateInfoResponse = UpdateInfoResponse.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();

        return ApiResponse.builder()
                .data(JsonObjectMapper.objectToJson(updateInfoResponse))
                .build();
    }

    @Transactional
    public ApiResponse updatePassword(Long id, UpdatePasswordRequest updatePasswordRequest) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new IllegalStateException("not fount");
        }
        User user = findUser.get();
        user.updatePassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

        return ApiResponse.builder()
                .message("비밀번호가 성공적으로 수정되었습니다.")
                .build();
    }
}
