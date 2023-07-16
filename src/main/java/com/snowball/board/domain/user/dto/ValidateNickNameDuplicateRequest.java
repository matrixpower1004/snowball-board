package com.snowball.board.domain.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ValidateNickNameDuplicateRequest {

    @NotEmpty
    @Length(max = 10)
    private String nickName;

}
