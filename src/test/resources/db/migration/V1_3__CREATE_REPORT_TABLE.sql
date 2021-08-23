CREATE TABLE report(
    reportId varchar(36) PRIMARY KEY,
    totalExpenses numeric(10,2) NOT NULL,
    totalIncomes numeric(10,2) NOT NULL,
    totalSavings numeric(10,2) NOT NULL,
    isExpense boolean NOT NULL,
    date timestamp NOT NULL

);