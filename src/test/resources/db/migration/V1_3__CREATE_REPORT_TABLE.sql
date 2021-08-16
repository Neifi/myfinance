CREATE TABLE report(
    userId varchar(36) PRIMARY KEY,
    totalExpenses numeric(10,2) NOT NULL,
    totalIncome numeric(10,2) NOT NULL,
    totalSavings numeric(10,2) NOT NULL,
    date timestamp NOT NULL

);