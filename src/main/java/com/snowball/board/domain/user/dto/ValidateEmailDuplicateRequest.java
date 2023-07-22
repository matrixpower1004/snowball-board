package com.snowball.board.domain.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ValidateEmailDuplicateRequest {

    @NotEmpty
    @Length(max = 30)
    @Email
    private String email;

}
