# 패스트캠퍼스 Toy Project III : 게시판 제작하기

## 게시판 기능 명세서
1. 회원가입
	- [ ]  [회원가입 페이지] 회원가입 구현 (id, username, password, email, nickName, role, createdAt, updatedAt) - role은 새싹 회원과 우수 회원으로 구분 (디폴트 : 새싹회원, 게시글 수 10 개 이상 우수 회원)
2. 로그인
	- [ ]  [로그인 페이지] 로그인 구현 (username, password)
3. 유저네임 중복체크
	- [ ]  [회원가입 페이지] 동일 username 중복체크하기
4. 회원정보보기
	- [ ]  [회원정보 페이지] username, email, role, createdAt 확인
5. 회원정보 수정하기
	- [ ]  [회원정보 수정페이지] email, nickName 변경가능
6. 비밀번호 수정하기
	- [ ]  [비밀번호 수정 페이지] 비밀번호 수정 구현
7. 게시글 카테고리
	- [ ]  새싹회원 게시판, 우수회원 게시판 구현 (게시판은 2개이지만 하나의 화면을 공유해서 사용하고 카테고리로 구분함)
8. 게시글 쓰기
	- [ ]  [게시글 쓰기 페이지] - 권한(새싹, 우수)에 따라 다른 게시판에 글이 적어짐 (썸머노트 적용)
9. 게시글 목록보기
	- [ ]  [게시글 목록보기 페이지] 게시글 목록보기 (id, title, content, thumbnail, user의 nickName 화면에 보여야 함, content내용을 화면에 2줄이 넘어가면 Ellipsis(...)으로 스타일 변경, 정렬은 id순 Desc
		- [x]  게시글 목록보기
		- [ ]  게시글 페이징

## 사용 언어 및 기술 스택
- Java 17
- Gradle
- Lombok
- Junit, AssertJ
- Springboot 2.7.13
- MyBatis 3
- Thymeleaf
- Spring Security

## 개발환경
- IntelliJ IDEA
- H2 DB

## 협업 툴
- Github Project
- Slack
- Zoom
## DB Table
```sql
-- status : Current Status of User. TRUE(Activated) / FALSE(UnActivated)
-- role : BLACK, BEGINNER, EXPERT, ADMIN
CREATE TABLE `user_tb`
(
    `id`           BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_account` VARCHAR(15)                           NOT NULL UNIQUE,
    `user_name`    VARCHAR(10)                           NOT NULL,
    `password`     VARCHAR(72)                           NOT NULL,
    `email`        VARCHAR(30)                           NOT NULL,
    `nick_name`    VARCHAR(10)                           NOT NULL UNIQUE,
    `user_status`  BOOLEAN     DEFAULT TRUE              NOT NULL,
    `user_role`    VARCHAR(20) DEFAULT 'BEGINNER'        NOT NULL,
    `created_at`   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL
) DEFAULT CHARSET = utf8mb4;


-- post(게시판)
CREATE TABLE `post`
(
    `id`          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`     BIGINT                              NOT NULL,
    `title`       VARCHAR(255)                        NOT NULL,
    `content`     TEXT                                NOT NULL,
    `blind_state` BOOLEAN   DEFAULT false, -- ture면 게시글 블라인드 상태
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) DEFAULT CHARSET = utf8mb4;


-- 게시글 신고 게시판. 하나의 게시글은 한 유저가 한 번만 신고할 수 있다.
create table `report_board`
(
    `id`             bigint primary key auto_increment,
    `post_id`        bigint       not null comment '신고 대상 게시글',
    `report_type`    varchar(20)  not null comment '신고 유형 : 욕설, 비방, 음란, 스팸,광고',
    `reporter_id`    bigint       not null comment '신고자 id',
    `report_img_url` varchar(255) not null comment '증거 스크린샷',
    `report_date`    timestamp    not null default now() comment '신고 일자',
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (reporter_id) REFERENCES user_tb (id),
    UNIQUE (post_id, reporter_id)
) DEFAULT CHARSET = utf8mb4;


-- comment(댓글)
CREATE TABLE `comment`
(
    `id`         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `post_id`    BIGINT                              NOT NULL,
    `user_id`    BIGINT                              NOT NULL,
    `content`    VARCHAR(50)                         NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) DEFAULT CHARSET = utf8mb4;


-- REPLY (대댓글)
CREATE TABLE `reply`
(
    `id`         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `comment_id` BIGINT      NOT NULL,
    `user_id`    BIGINT      NOT NULL,
    `content`    VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (comment_id) REFERENCES comment(id),
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) DEFAULT CHARSET = utf8mb4;
```
### DB 테이블 및 테스트용 더미 데이터는 애플리케이션 시작 시 자동으로 insert 됩니다.
## ERD
![Image](https://github.com/matrixpower1004/snowball-board-admin/assets/104916288/1c872b8a-aeea-4372-b95b-e495141b2143)
## 팀원 역할 분담
- 팀장 : [이지상] (https://github.com/matrixpower1004/snowball-board-admin)
  * 프로젝트 셋업
  * 신고 게시판, 게시판, 댓글(대댓글) DB 테이블 설계
  * 테스트용 더미 데이터 생성
  * Admin 기능
  * [API명세서] (https://docs.google.com/spreadsheets/d/1qEEMAMGnR2dmHo5dzFqQ1OJ8OIxQWEr2DDqrvdOPgFo/edit#gid=0)
- 팀원 : [배종윤] (https://github.com/jy-b)
  * 미완성된 상태라 각자 맡은 부분의 최신버젼입니다
  * https://github.com/jy-b/snowball-board/tree/feature/user-refactoring
- 팀원 : [박민기] (https://github.com/Coding9nt)
  * 게시판 기능
- 팀원 : [박성욱] (https://github.com/gosuuk)
  * 댓글, 대댓글 기능
## 프로젝트 후기
- 팀장 : 이지상
  * 멀티 모듈 프로젝트라는 개념을 알지 못해서 중간에 프로젝트 자체를 완전히 분리하는 시행 착오를 겪은 점이 아쉬웠습니다.
  * 제일 먼저 화면을 대략적으로 구상하고 프로젝트를 시작했어야 했는데, 보여줄 화면에 대한 구상 없이 DB 설계부터 하다보니 많은 시행착오와 테이블 변경을 해야 했던 점이 아쉬웠습니다.
  * 기능을 작은 단위로 쪼개서 핵심 기능부터 구현하고, 점차 부가적인 기능 구현으로 진행하지 못한 점이 아쉬웠습니다.
  * Admin의 핵심 기능이 아닌 게시판 페이징 처리에 너무 많은 시간을 투자하여, 반드시 구현해야 할 핵심 기능을 제대로 구현하지 못한 점이 아쉬웠습니다.
  * 팀장으로서 프로젝트를 제대로 마무리 짓지 못한 점이 아쉬웠습니다.

  
