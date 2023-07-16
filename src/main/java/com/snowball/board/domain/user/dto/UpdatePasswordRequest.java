package com.snowball.board.domain.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdatePasswordRequest {

    @NotEmpty
    private String currentPassword;

    @NotEmpty
    @Length(min = 10, max = 72)
    private String newPassword;

}
