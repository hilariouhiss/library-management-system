USE hiss_db;

-- 创建用户表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `user_id`  INT         NOT NULL AUTO_INCREMENT, -- 用户Id，自增
    `username` VARCHAR(45) NOT NULL UNIQUE,         -- 用户名
    `password` VARCHAR(60) NOT NULL,                -- 密码
    `email`    VARCHAR(45) NOT NULL UNIQUE,         -- 邮箱
    `role`     VARCHAR(5)  NOT NULL,                -- 角色
    PRIMARY KEY (`user_id`)                         -- 用户Id为主键
);

-- 创建用户名索引
CREATE INDEX `idx_username` ON `t_user` (`username`);

-- 创建图书表
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`
(
    `book_id`      INT          NOT NULL AUTO_INCREMENT, -- 图书Id，自增
    `title`        VARCHAR(100) NOT NULL,                -- 标题
    `author`       VARCHAR(45)  NOT NULL,                -- 作者
    `publisher`    VARCHAR(45)  NOT NULL,                -- 出版商
    `publish_date` DATE         NOT NULL,                -- 出版日期
    `isbn`         VARCHAR(20)  NOT NULL,                -- ISBN
    `quantity`     INT          NOT NULL,                -- 数量
    PRIMARY KEY (`book_id`)                              -- 图书Id为主键
);

-- 创建标题，ISBN 索引
CREATE INDEX `idx_title` ON `t_book` (`title`);
CREATE INDEX `idx_isbn` ON `t_book` (`isbn`);

-- 创建借阅表
DROP TABLE IF EXISTS `t_borrow`;
CREATE TABLE `t_borrow`
(
    `borrow_id`   INT  NOT NULL AUTO_INCREMENT,              -- 借阅Id，自增
    `user_id`     INT  NOT NULL,                             -- 用户Id
    `book_id`     INT  NOT NULL,                             -- 图书Id
    `borrow_date` DATE NOT NULL,                             -- 借阅日期
    `return_date` DATE,                                      -- 归还日期
    `status`      INT  NOT NULL,                             -- 状态
    PRIMARY KEY (`borrow_id`),                               -- 借阅Id为主键
    FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`), -- 用户Id外键，引用用户表的用户Id
    FOREIGN KEY (`book_id`) REFERENCES `t_book` (`book_id`)  -- 图书Id外键，引用图书表的图书Id
);

-- 插入图书数据
INSERT INTO `t_book` (`title`, `author`, `publisher`, `publish_date`, `isbn`, `quantity`)
VALUES ('Book Title 1', 'Author 1', 'Publisher 1', '2022-01-01', 'ISBN1', 10),
       ('Book Title 2', 'Author 2', 'Publisher 2', '2022-02-02', 'ISBN2', 20),
       ('Book Title 3', 'Author 3', 'Publisher 3', '2022-03-03', 'ISBN3', 30),
       ('Book Title 4', 'Author 4', 'Publisher 4', '2022-04-04', 'ISBN4', 40),
       ('Book Title 5', 'Author 5', 'Publisher 5', '2022-05-05', 'ISBN5', 50);

-- 插入用户数据
INSERT INTO `t_user` (`username`, `password`, `email`, `role`)
VALUES ('admin', '$2a$10$OmaNzwYz8FcMEaf0vyCAo.5XMJsDHuIMtrrBDrjDLKnv4uNjsO1ci', 'admin@example.com', 'ADMIN'),
       ('user', '$2a$10$OmaNzwYz8FcMEaf0vyCAo.5XMJsDHuIMtrrBDrjDLKnv4uNjsO1ci', 'user@example.com', 'USER');

-- 插入借阅数据
INSERT INTO `t_borrow` (`user_id`, `book_id`, `borrow_date`, `return_date`, `status`)
VALUES (1, 1, '2020-01-01', '2020-01-15', 1),
       (1, 2, '2020-01-02', '2020-01-16', 0),
       (1, 3, '2020-01-03', '2020-01-17', 0),
       (2, 4, '2020-01-04', '2020-01-18', 1),
       (2, 5, '2020-01-05', '2020-01-19', 0);