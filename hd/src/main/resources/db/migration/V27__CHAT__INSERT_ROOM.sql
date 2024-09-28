INSERT INTO CHAT.tb_room (room_status, fk_user_customer, fk_user_employee, fk_sector) VALUES 
(3, 3, 2, 1), 
(0, 3, null, 3), 
(1, 3, 2, 1);

INSERT INTO CHAT.TB_ROOM (room_status, fk_user_customer, fk_user_employee, fk_sector, reason, solution, close_datetime) VALUES
(4, 3, 2, 2, 'Cliente solicitou ajuda em....', 'Cliente ajudado no quesito...', current_timestamp);
