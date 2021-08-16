CREATE TABLE registry(
    userId varchar(36) NOT NULL,
    registryId varchar(36) PRIMARY KEY NOT NULL,
    category varchar (10),
    name varchar (20) NOT NULL,
    cost numeric (10,2) NOT NULL,
    currency varchar (5) NOT NULL,
    date timestamp NOT NULL,
    isExpense boolean NOT NULL
);