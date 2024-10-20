INSERT INTO kanban.tb_stage (fk_task, step_number,description,fk_user_from,fk_user_to,fk_department_to) VALUES
(1, 1, 'Corrigir...', 7, NULL, 4),
(2, 1, 'Verificar possibilidade...', 7, 2, 1),
(2, 2, 'Implementar...', 2, 6, NULL),
(2, 3, 'Testar e entregar...', 6, 7, NULL),
(2, 4, 'Entregue e validado...', 7, NULL, NULL);
