package com.snowball.board.domain.user.api;

import com.snowball.board.domain.user.dto.UpdateInfoRequest;
import com.snowball.board.domain.user.dto.UpdatePasswordRequest;
import com.snowball.board.domain.user.dto.ValidateEmailDuplicateRequest;
import com.snowball.board.domain.user.dto.ValidateNickNameDuplicateRequest;
import com.snowball.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserInfo(id), HttpStatus.OK);
    }

    @PostMapping("/check-nickName")
    public ResponseEntity<?> validateNickNameDuplicate(@RequestBody @Valid ValidateNickNameDuplicateRequest validateNickNameDuplicateRequest) {

        return new ResponseEntity<>(userService.validateDuplicateNickName(validateNickNameDuplicateRequest), HttpStatus.OK);
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> validateEmailDuplicate(@RequestBody @Valid ValidateEmailDuplicateRequest validateEmailDuplicateRequest) {

        return new ResponseEntity<>(userService.validateDuplicateEmail(validateEmailDuplicateRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest, @PathVariable Long id) {

        return new ResponseEntity<>(userService.updatePassword(id, updatePasswordRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserInfo(@RequestBody @Valid UpdateInfoRequest updateInfoRequest, @PathVariable Long id) {

        return new ResponseEntity<>(userService.updateUserInfo(id, updateInfoRequest), HttpStatus.OK);
    }

}
