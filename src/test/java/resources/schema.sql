CREATE TABLE if not exists department
(
  id     INTEGER NOT NULL CONSTRAINT department_pkey PRIMARY KEY,
  name   VARCHAR(80) NOT NULL CONSTRAINT idx_department_name UNIQUE,
  closed BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE if not exists person
(
  id          INTEGER NOT NULL CONSTRAINT person_pkey PRIMARY KEY,
  first_name  VARCHAR(80) NOT NULL,
  last_name   VARCHAR(80) NOT NULL,
  middle_name VARCHAR(80),
  age         INTEGER,
  department_id INTEGER references department
);