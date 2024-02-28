INSERT INTO `t_user`(`created_at`, `nm`, `uid`, `upw`, `role`) VALUES ('2024-02-15 11:47:24.598049', '유저1', 'mic1', '$2a$10$4vSF/JwbZksVB1GkZYwVNOceSu.fNPwbypGjgl7IiviDE/RN6nJIW', 'USER');
INSERT INTO `t_user`(`created_at`, `nm`, `uid`, `upw`, `role`) VALUES ('2024-02-15 11:47:24.598049', '유저2', 'mic2', '$2a$10$4vSF/JwbZksVB1GkZYwVNOceSu.fNPwbypGjgl7IiviDE/RN6nJIW', 'USER');
INSERT INTO `t_user`(`created_at`, `nm`, `uid`, `upw`, `role`) VALUES ('2024-02-15 11:47:24.598049', '유저3', 'mic3', '$2a$10$4vSF/JwbZksVB1GkZYwVNOceSu.fNPwbypGjgl7IiviDE/RN6nJIW', 'USER');
INSERT INTO `t_user`(`created_at`, `nm`, `uid`, `upw`, `role`) VALUES ('2024-02-15 11:47:24.598049', '유저4', 'mic4', '$2a$10$4vSF/JwbZksVB1GkZYwVNOceSu.fNPwbypGjgl7IiviDE/RN6nJIW', 'USER');
INSERT INTO `t_user`(`created_at`, `nm`, `uid`, `upw`, `role`) VALUES ('2024-02-15 11:47:24.598049', '유저5', 'mic5', '$2a$10$4vSF/JwbZksVB1GkZYwVNOceSu.fNPwbypGjgl7IiviDE/RN6nJIW', 'USER');


INSERT INTO `t_chatroom` (`created_at`, `ichat_room`, `last_msg_at`, `last_msg`) VALUES ('2024-02-27 11:44:23.332838', 1, '2024-02-27 11:44:23.378059', NULL);
INSERT INTO `t_chatroom` (`created_at`, `ichat_room`, `last_msg_at`, `last_msg`) VALUES ('2024-02-27 11:44:23.332838', 2, '2024-02-27 11:44:23.378059', NULL);

INSERT INTO `t_chatroom_user` (`ichat_room`, `ichat_room_user`, `iuser`) VALUES (1, 1, 1);
INSERT INTO `t_chatroom_user` (`ichat_room`, `ichat_room_user`, `iuser`) VALUES (1, 2, 3);
INSERT INTO `t_chatroom_user` (`ichat_room`, `ichat_room_user`, `iuser`) VALUES (2, 3, 1);
INSERT INTO `t_chatroom_user` (`ichat_room`, `ichat_room_user`, `iuser`) VALUES (2, 4, 4);

