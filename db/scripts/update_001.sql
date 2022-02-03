 create table if not exists post(
 	id serial primary key,
 	name VARCHAR(255),
 	text text,
 	link text unique not null,
 	created TIMESTAMP
 );