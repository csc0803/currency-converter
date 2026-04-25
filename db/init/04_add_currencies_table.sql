IF NOT EXISTS (
    SELECT name FROM sys.tables WHERE name = 'currencies'
)
BEGIN
    CREATE TABLE currency (
        id			INT IDENTITY(1,1)	NOT NULL,
        code		CHAR(3)				NOT NULL,
        is_active	BIT					NOT NULL,
        updated_at	DATETIME2			NOT NULL DEFAULT GETDATE(),

        CONSTRAINT PK_currency PRIMARY KEY (id),
		CONSTRAINT UQ_currency_code UNIQUE (code)
    );

    PRINT 'currencies 建立成功';
END
ELSE
BEGIN
    PRINT 'currencies 已存在，略過';
END