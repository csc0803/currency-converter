USE master;

-- 避免重複建立報錯
IF NOT EXISTS (
    SELECT name FROM sys.databases WHERE name = 'currency_db'
)
BEGIN
    CREATE DATABASE currency_db;
    PRINT 'currency_db 建立成功';
END
ELSE
BEGIN
    PRINT 'currency_db 已存在，略過';
END