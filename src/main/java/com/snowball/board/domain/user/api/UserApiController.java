package com.snowball.board.domain.user.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snowball.board.common.util.ApiResponse;
import com.snowball.board.domain.auth.dto.AuthenticationResponse;
import com.snowball.board.domain.user.dto.*;
import com.snowball.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // TODO: 2023-07-19 api for check duplicate email 

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserInfo(@PathVariable Long id) throws JsonProcessingException {

        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @PostMapping("/check-nickName")
    public ResponseEntity<ApiResponse> validateNickNameDuplicate(@RequestBody @Valid ValidateNickNameDuplicateRequest validateNickNameDuplicateRequest) {

        return ResponseEntity.ok(userService.validateDuplicateNickName(validateNickNameDuplicateRequest));
    }

    @PostMapping("/check-email")
    public ResponseEntity<ApiResponse> validateEmailDuplicate(@RequestBody @Valid ValidateEmailDuplicateRequest validateEmailDuplicateRequest) {

        return ResponseEntity.ok(userService.validateDuplicateEmail(validateEmailDuplicateRequest));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest, @PathVariable Long id) {

        return ResponseEntity.ok(userService.updatePassword(id, updatePasswordRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUserInfo(@RequestBody @Valid UpdateInfoRequest updateInfoRequest, @PathVariable Long id) throws JsonProcessingException {

        return ResponseEntity.ok(userService.updateUserInfo(id, updateInfoRequest));
    }

}
