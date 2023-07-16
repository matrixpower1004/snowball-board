package com.snowball.board.domain.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterRequest {

    // Login ID
    @NotEmpty
    @Length(max = 15)
    private String userAccount;

    // User Name
    @NotEmpty
    @Length(max = 10)
    private String userName;

    @NotEmpty
    @Length(min = 10)
    private String password;

    @NotEmpty
    @Length(max = 30)
    private String email;

    @NotEmpty
    @Length(max = 10)
    private String nickName;

}
