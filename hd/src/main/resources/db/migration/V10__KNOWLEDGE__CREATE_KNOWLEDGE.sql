CREATE TABLE IF NOT EXISTS KNOWLEDGE.TB_KNOWLEDGE (
    ID SERIAL CONSTRAINT PK_TB_KNOWLEDGE PRIMARY KEY,
    TITLE VARCHAR(100) NOT NULL CONSTRAINT UC_TB_KNOWLEDGE_TITLE UNIQUE,
    FK_SOFTWARE INT NOT NULL,
    CONTENT TEXT NOT NULL
);
 
ALTER TABLE KNOWLEDGE.TB_KNOWLEDGE ADD CONSTRAINT FK_SOFTWARE
    FOREIGN KEY (FK_SOFTWARE)
    REFERENCES KNOWLEDGE.TB_SOFTWARE (ID);