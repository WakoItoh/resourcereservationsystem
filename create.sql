
/* Drop Tables */

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS attendance_types;
DROP TABLE IF EXISTS resource_properties;
DROP TABLE IF EXISTS resources;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS offices;
DROP TABLE IF EXISTS properties;
DROP TABLE IF EXISTS users;




/* Create Tables */

CREATE TABLE attendance_types
(
	attendance_type_id serial NOT NULL UNIQUE,
	attendance_type_name varchar(30) NOT NULL,
	PRIMARY KEY (attendance_type_id)
) WITHOUT OIDS;


CREATE TABLE categories
(
	category_id serial NOT NULL UNIQUE,
	category_name varchar(30) NOT NULL,
	PRIMARY KEY (category_id)
) WITHOUT OIDS;


CREATE TABLE offices
(
	office_id serial NOT NULL UNIQUE,
	office_name varchar(30) NOT NULL,
	office_location varchar(100),
	PRIMARY KEY (office_id)
) WITHOUT OIDS;


CREATE TABLE properties
(
	property_id serial NOT NULL UNIQUE,
	property_name varchar(30) NOT NULL,
	PRIMARY KEY (property_id)
) WITHOUT OIDS;


CREATE TABLE reservations
(
	reservation_id serial NOT NULL UNIQUE,
	use_start timestamp NOT NULL,
	use_end timestamp NOT NULL,
	resource_id int NOT NULL,
	meeting_name varchar(30) NOT NULL,
	reserver_id varchar(8) NOT NULL,
	co_reserver_id varchar(8),
	attendance_count int NOT NULL,
	attendance_type_id int,
	note varchar(500),
	PRIMARY KEY (reservation_id)
) WITHOUT OIDS;


CREATE TABLE resources
(
	resource_id serial NOT NULL UNIQUE,
	resource_name varchar(30) NOT NULL,
	category_id int NOT NULL,
	capacity int DEFAULT 0 NOT NULL,
	office_id int NOT NULL,
	note varchar(500),
	suspend_start timestamp,
	suspend_end timestamp,
	deleted int DEFAULT 0 NOT NULL,
	PRIMARY KEY (resource_id)
) WITHOUT OIDS;


CREATE TABLE resource_properties
(
	resource_id int NOT NULL,
	property_id int NOT NULL,
	PRIMARY KEY (resource_id, property_id)
) WITHOUT OIDS;


CREATE TABLE users
(
	id varchar(8) NOT NULL UNIQUE,
	password varchar(64) NOT NULL,
	last_name varchar(20) NOT NULL,
	first_name varchar(20) NOT NULL,
	user_level int DEFAULT 1 NOT NULL,
	phone varchar(20),
	email varchar(100),
	PRIMARY KEY (id)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE reservations
	ADD FOREIGN KEY (attendance_type_id)
	REFERENCES attendance_types (attendance_type_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE resources
	ADD FOREIGN KEY (category_id)
	REFERENCES categories (category_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE resources
	ADD FOREIGN KEY (office_id)
	REFERENCES offices (office_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE resource_properties
	ADD FOREIGN KEY (property_id)
	REFERENCES properties (property_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reservations
	ADD FOREIGN KEY (resource_id)
	REFERENCES resources (resource_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE resource_properties
	ADD FOREIGN KEY (resource_id)
	REFERENCES resources (resource_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reservations
	ADD FOREIGN KEY (reserver_id)
	REFERENCES users (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reservations
	ADD FOREIGN KEY (co_reserver_id)
	REFERENCES users (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



