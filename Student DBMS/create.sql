

connect to cs157a;
drop table p1.customer;
drop table p1.account;
drop view account_view;


Create table p1.customer (
	id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY 
		(START WITH 100 INCREMENT BY 1, NO CACHE),
	name varchar(15),
	gender char(1) CHECK (Gender in('F', 'f', 'm', 'M')),
	age int CHECK (Age >= 0),
	pin int CHECK (Pin >= 0));

Create table p1.account (
	number int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1000 INCREMENT BY 1, NO CACHE ),
	id int NOT NULL REFERENCES p1.customer(Id) ON DELETE CASCADE,
	balance int NOT NULL CHECK (Balance >= 0),
	type char(1) NOT NULL CHECK (Type in ('C','c','s','S')),
	status char(1) NOT NULL CHECK (Status in ('A', 'a', 'i','I')));

insert into p1.customer values (0,'admin','F',0,0);




-- Optional test data

insert into p1.customer (Name,Gender,Age,Pin) values ('Jeremy Jones','M',33,4173)^
insert into p1.customer (Name,Gender,Age,Pin) values ('David Gilmore','M',40,9773)^
insert into p1.customer (Name,Gender,Age,Pin) values ('Sammy Hagar','M',40,8080)^
insert into p1.customer (Name,Gender,Age,Pin) values ('Lauri Jones','F',62,8067)^
insert into p1.customer (Name,Gender,Age,Pin) values ('Peter boyle','M',62,1518)^
insert into p1.customer (Name,Gender,Age,Pin) values ('Bivi Lazo','F',33,9770)^


insert into p1.account (id, balance, type, status) values (105,100,'C','A')^
insert into p1.account (id, balance, type, status) values (104,5000,'S','A')^
insert into p1.account (id, balance, type, status) values (104,1235,'C','A')^
insert into p1.account (id, balance, type, status) values (103,1635,'C','A')^
insert into p1.account (id, balance, type, status) values (102,35000,'S','A')^
insert into p1.account (id, balance, type, status) values (102,2500,'C','A')^
insert into p1.account (id, balance, type, status) values (105,300,'C','A')^
insert into p1.account (id, balance, type, status) values (105,0,'C','I')^
insert into p1.account (id, balance, type, status) values (101,1670,'C','A')^
insert into p1.account (id, balance, type, status) values (100,3064,'C','A')^
