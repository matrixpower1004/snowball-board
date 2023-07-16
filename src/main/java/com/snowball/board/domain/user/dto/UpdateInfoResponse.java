package com.snowball.board.domain.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfoResponse {

    @Length(max = 30)
    private String email;

    @Length(max = 10)
    private String nickName;

}
