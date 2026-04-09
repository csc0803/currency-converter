USE currency_db;

-- 避免重複建立報錯
IF NOT EXISTS (
    SELECT name FROM sys.database_principals WHERE name = 'currency_user'
)
BEGIN
    CREATE USER currency_user FOR LOGIN currency_user;
    PRINT 'User currency_user 建立成功';
END
ELSE
BEGIN
    PRINT 'User currency_user 已存在，略過';
END
GO

-- 給予資料操作權限
ALTER ROLE db_datareader ADD MEMBER currency_user;  -- SELECT
ALTER ROLE db_datawriter ADD MEMBER currency_user;  -- INSERT, UPDATE, DELETE
GO

-- JPA ddl-auto=update 需要建表/改表權限
GRANT CREATE TABLE TO currency_user;
GRANT ALTER ON SCHEMA::dbo TO currency_user;
GO