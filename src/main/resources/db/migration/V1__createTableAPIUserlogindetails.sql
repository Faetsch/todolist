CREATE TABLE apiuserlogindetails
(
	id SERIAL PRIMARY KEY,
	password VARCHAR(255) not null,
	username VARCHAR(255) not null UNIQUE
);