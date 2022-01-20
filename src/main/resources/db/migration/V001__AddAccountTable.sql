create table if not exists account(
    account_id varchar(20) primary key not null,
    account_type varchar(20),
    customer_id varchar(20),
    balance numeric);