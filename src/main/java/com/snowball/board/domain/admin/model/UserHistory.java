package com.snowball.board.domain.admin.model;

import com.snowball.board.domain.admin.code.ReportType;
import com.snowball.board.domain.admin.code.UserRole;
import lombok.*;

import java.sql.Timestamp;

/**
 * author         : Jason Lee
 * date           : 2023-07-12
 * description    :
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UserHistory {

    private Long id;
    private Long memberId;
    private UserRole userRole; // 관리자가 변경한 회원의 역할
    private ReportType reportType; // 관리자의 회원 레벨 변경 사유
    private Long adminId; // 관리자의 id
    private Timestamp createdAt;

}
