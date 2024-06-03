CREATE TABLE IF NOT EXISTS AUTH.TB_USER (
    ID SERIAL CONSTRAINT PK_TB_USER PRIMARY KEY,
    USERNAME VARCHAR(255) CONSTRAINT UC_TB_USER_USERNAME UNIQUE NOT NULL,
    FULLNAME VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    ACCOUNT_NON_EXPIRED BOOLEAN DEFAULT TRUE NOT NULL,
    ACCOUNT_NON_LOCKED BOOLEAN DEFAULT TRUE NOT NULL,
    CREDENTIALS_NON_EXPIRED BOOLEAN DEFAULT TRUE NOT NULL,
    ENABLED BOOLEAN DEFAULT TRUE NOT NULL
);
