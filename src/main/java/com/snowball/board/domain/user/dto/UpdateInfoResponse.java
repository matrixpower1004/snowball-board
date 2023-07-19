package com.snowball.board.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateInfoResponse {

    @Length(max = 30)
    private String email;

    @Length(max = 10)
    private String nickName;

}
