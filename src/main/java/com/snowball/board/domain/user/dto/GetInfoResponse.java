package com.snowball.board.domain.user.dto;

import com.snowball.board.common.util.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInfoResponse {

    private String userName;

    private String email;

    private String nickName;

    private UserRole userRole;

    private Timestamp createdAt;

}
