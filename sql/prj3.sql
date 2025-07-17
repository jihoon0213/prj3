# 게시물 테이블
CREATE TABLE board
(
    id          INT AUTO_INCREMENT NOT NULL,
    title       VARCHAR(300)       NOT NULL,
    content     VARCHAR(10000)     NOT NULL,
    author      VARCHAR(255)       NOT NULL,
    inserted_at datetime           NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_board PRIMARY KEY (id),
    FOREIGN KEY (author) REFERENCES member (email)
);
DROP TABLE board;

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

#권한 테이블
CREATE TABLE auth
(
    member_email VARCHAR(255) NOT NULL,
    auth_name    VARCHAR(255) NOT NULL,
    PRIMARY KEY (member_email, auth_name),
    FOREIGN KEY (member_email) REFERENCES member (email)
);
INSERT INTO auth
    (member_email, auth_name)
VALUES ('admin@admin.com', 'admin');
SELECT *
FROM auth;

# 검색 테스트용 데이터
INSERT INTO board
    (title, content, author)
VALUES ('qwe', 'asd', '99@99.com'),
       ('zxc', '123', '88@88.com'),
       ('456', 'rty', '99@99.com'),
       ('fgh', 'vbn', '88@88.com'),
       ('789', 'uio', '99@99.com'),
       ('jkl', 'nmp', '88@88.com');

# 페이지 테스트용 데이터
INSERT INTO board
    (title, content, author)
SELECT title, content, author
FROM board;

SELECT COUNT(*)
FROM board;

# 댓글 테이블
CREATE TABLE comment
(
    id          INT AUTO_INCREMENT NOT NULL,
    board_id    INT                NOT NULL,
    author      VARCHAR(255)       NOT NULL,
    comment     VARCHAR(2000)      NOT NULL,
    inserted_at datetime           NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_comment PRIMARY KEY (id),
    FOREIGN KEY (author) REFERENCES member (email),
    FOREIGN KEY (board_id) REFERENCES board (id)
);


#좋아요 테이블
CREATE TABLE board_like
(
    board_id     INT          NOT NULL,
    member_email VARCHAR(255) NOT NULL,
    PRIMARY KEY (board_id, member_email),
    FOREIGN KEY (board_id) REFERENCES board (id),
    FOREIGN KEY (member_email) REFERENCES member (email)
);

#파일 테이블
CREATE TABLE board_file
(
    board_id INT          NOT NULL,
    name     VARCHAR(300) NOT NULL,
    PRIMARY KEY (board_id, name),
    FOREIGN KEY (board_id) REFERENCES board (id)
);
