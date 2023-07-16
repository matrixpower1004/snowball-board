package com.snowball.board.domain.auth.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuthenticationRequest {

    @NotEmpty
    private String userAccount;
    @NotEmpty
    private String password;
}
