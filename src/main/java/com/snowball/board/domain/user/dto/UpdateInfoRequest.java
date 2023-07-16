package com.snowball.board.domain.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateInfoRequest {

    @NotEmpty
    @Length(max = 30)
    private String email;

    @NotEmpty
    @Length(max = 10)
    private String nickName;

}
