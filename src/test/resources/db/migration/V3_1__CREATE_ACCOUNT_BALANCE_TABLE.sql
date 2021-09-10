CREATE TABLE account_balance(
    userId varchar(36) NOT NULL PRIMARY KEY,
    total_balance numeric (12,2) NOT NULL,
    currency varchar (5) NOT NULL,
    date timestamptz NOT NULL
);
