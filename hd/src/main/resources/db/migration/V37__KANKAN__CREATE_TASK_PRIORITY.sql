CREATE TABLE KANBAN.TB_TASK_PRIORITY (
    ID SERIAL CONSTRAINT PK_TB_TASK_PRIORITY PRIMARY KEY,
    DESCRIPTION VARCHAR(30) NOT NULL CONSTRAINT UC_TB_TASK_PRIORITY_DESCRIPTION UNIQUE
);
