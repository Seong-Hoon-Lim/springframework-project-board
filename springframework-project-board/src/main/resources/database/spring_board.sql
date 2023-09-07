USE spring_board;
CREATE TABLE spring_board.member
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `account`   VARCHAR(50)  NOT NULL,
    `password`  VARCHAR(255) NOT NULL,
    `name`      VARCHAR(50)  NOT NULL,
    `gender`    VARCHAR(4)   NOT NULL,
    `birth`     VARCHAR(10)  NOT NULL,
    `email`     VARCHAR(50)  NOT NULL,
    `phone`     VARCHAR(13)  NOT NULL,
    `zipcode`   VARCHAR(5)   NOT NULL,
    `addr1`     VARCHAR(100) NOT NULL,
    `addr2`     VARCHAR(100) NOT NULL,
    `createdAt` DATETIME     NOT NULL DEFAULT now(),
    `updatedAt` DATETIME     NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE spring_board.board
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `memberId`   BIGINT       NOT NULL,
    `memberName` VARCHAR(50)  NOT NULL,
    `title`      VARCHAR(255) NOT NULL,
    `content`    TEXT         NOT NULL,
    `hit`        INT          NULL     DEFAULT 0,
    `ip`         VARCHAR(20)  NOT NULL,
    `rippleCnt`  INT          NULL     DEFAULT 0,
    `fileName`   VARCHAR(100) NULL     DEFAULT NULL,
    `fileSize`   BIGINT       NULL     DEFAULT 0,
    `createdAt`  DATETIME     NOT NULL DEFAULT now(),
    `updatedAt`  DATETIME     NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`memberId`) REFERENCES spring_board.`member` (`id`)
        ON DELETE CASCADE
) DEFAULT CHARSET = UTF8;

CREATE TABLE spring_board.ripple
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `boardId`    BIGINT      NOT NULL,
    `memberId`   BIGINT      NOT NULL,
    `memberName` VARCHAR(10) NOT NULL,
    `comment`    TEXT        NOT NULL,
    `ip`         VARCHAR(20),
    `createdAt`  DATETIME    NOT NULL DEFAULT now(),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`boardId`) REFERENCES spring_board.board (`id`)
        ON DELETE CASCADE,
    FOREIGN KEY (`memberId`) REFERENCES spring_board.`member` (`id`)
        ON DELETE CASCADE
) DEFAULT CHARSET = UTF8;


-- 게시판 더미 데이터 생성
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Bend of the River', 'Vestibulum sed magna at nunc commodo placerat. Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula.', 0, '127.210.190.176', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Ghost Rider', 'In sagittis dui vel nisl. Duis ac nibh.', 0, '173.255.85.215', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'They Gave Him A Gun', 'Aenean fermentum.', 0, '17.239.71.45', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Love Letter ', 'Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus.', 0, '100.112.37.140', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', '3, 2, 1... Frankie Go Boom (Frankie Go Boom)', 'Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo. Aliquam quis turpis eget elit sodales scelerisque.', 0, '200.177.112.15', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Sleeping Beauty', 'Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus. Phasellus in felis.', 0, '67.64.38.170', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Prophecy', 'Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh. Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros. Vestibulum ac est lacinia nisi venenatis tristique.', 0, '108.3.244.81', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Ollin oppivuodet', 'Ut at dolor quis odio consequat varius. Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla.', 0, '27.172.15.144', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Fists in the Pocket (Pugni in tasca, I)', 'Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla.', 0, '59.149.139.232', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Dragon Hunter', 'Integer tincidunt ante vel ipsum.', 0, '226.59.220.231', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Comanche Territory (Territorio comanche)', 'Integer a nibh. In quis justo.', 0, '99.185.70.87', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Air Guitar Nation', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.', 0, '187.131.45.55', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Mask', 'Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum. Proin eu mi.', 0, '135.132.230.178', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Loving You', 'In congue. Etiam justo. Etiam pretium iaculis justo. In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius.', 0, '177.150.233.250', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Pet Sematary', 'Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. In quis justo. Maecenas rhoncus aliquam lacus.', 0, '38.41.87.232', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Porco Rosso (Crimson Pig) (Kurenai no buta)', 'Aliquam non mauris.', 0, '80.32.62.83', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Waitress', 'Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem. Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', 0, '39.12.127.183', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Ring of Bright Water', 'Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh.', 0, '25.195.164.122', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Saratov Approach, The', 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat. Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.', 0, '1.41.123.131', 0, null, 0);
insert into spring_board.board (`memberId`, `memberName`, title, content, hit, ip, `rippleCnt`, `fileName`, `fileSize`)values (1, 'hooney', 'Train Ride to Hollywood', 'Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem. Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.', 0, '195.171.50.20', 0, null, 0);


