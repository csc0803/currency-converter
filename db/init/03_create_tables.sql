USE currency_db;

-- Table 1: exchange_rates（只存最新匯率）
IF NOT EXISTS (
    SELECT name FROM sys.tables WHERE name = 'exchange_rates'
)
BEGIN
    CREATE TABLE exchange_rates (
        id              BIGINT IDENTITY(1,1) NOT NULL,
        base_currency   CHAR(3)              NOT NULL,
        target_currency CHAR(3)              NOT NULL,
        rate            DECIMAL(18, 6)       NOT NULL,
        updated_at      DATETIME2            NOT NULL,

        CONSTRAINT PK_exchange_rates PRIMARY KEY (id),

        -- 同一個幣別對只存一筆，更新時用 MERGE
        CONSTRAINT UQ_exchange_rates_pair UNIQUE (base_currency, target_currency)
    );

    PRINT 'exchange_rates 建立成功';
END
ELSE
BEGIN
    PRINT 'exchange_rates 已存在，略過';
END

-- Table 2: exchange_rate_histories（歷史紀錄）
IF NOT EXISTS (
    SELECT name FROM sys.tables WHERE name = 'exchange_rate_histories'
)
BEGIN
    CREATE TABLE exchange_rate_histories (
        id              BIGINT IDENTITY(1,1) NOT NULL,
        base_currency   CHAR(3)              NOT NULL,
        target_currency CHAR(3)              NOT NULL,
        rate            DECIMAL(18, 6)       NOT NULL,
        fetched_at      DATETIME2            NOT NULL,

        CONSTRAINT PK_exchange_rate_histories PRIMARY KEY (id)
    );

    PRINT 'exchange_rate_histories 建立成功';
END
ELSE
BEGIN
    PRINT 'exchange_rate_histories 已存在，略過';
END

-- Table 3: conversion_records（操作紀錄）
IF NOT EXISTS (
    SELECT name FROM sys.tables WHERE name = 'conversion_records'
)
BEGIN
    CREATE TABLE conversion_records (
        id               BIGINT IDENTITY(1,1) NOT NULL,
        from_currency    CHAR(3)              NOT NULL,
        to_currency      CHAR(3)              NOT NULL,
        amount           DECIMAL(15, 4)       NOT NULL,
        converted_amount DECIMAL(15, 4)       NOT NULL,
        exchange_rate    DECIMAL(18, 6)       NOT NULL,
        created_at       DATETIME2            NOT NULL DEFAULT GETDATE(),

        CONSTRAINT PK_conversion_records PRIMARY KEY (id)
    );

    PRINT 'conversion_records 建立成功';
END
ELSE
BEGIN
    PRINT 'conversion_records 已存在，略過';
END