# 게시물 테이블
CREATE TABLE board
(
    id          INT AUTO_INCREMENT NOT NULL,
    title       VARCHAR(300)       NOT NULL,
    content     VARCHAR(10000)     NOT NULL,
    author      VARCHAR(100)       NOT NULL,
    inserted_at datetime           NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_board PRIMARY KEY (id)
);

#회원 테이블
CREATE TABLE member
(
    email       VARCHAR(255)  NOT NULL,
    password    VARCHAR(255)  NOT NULL,
    nick_name   VARCHAR(255)  NOT NULL UNIQUE,
    info        VARCHAR(3000) NULL,
    inserted_at datetime      NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_member PRIMARY KEY (email)
);

DROP TABLE member;
