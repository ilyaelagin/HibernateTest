CREATE DATABASE test2;

CREATE TABLE customers
(
    id      SERIAL PRIMARY KEY,
    tabnum  INT UNIQUE,
    name    VARCHAR(32) NOT NULL,
    surname VARCHAR(32) NOT NULL,
    email   VARCHAR(32),
    birth   DATE

);

CREATE TABLE autos
(
    id    SERIAL PRIMARY KEY,
    model VARCHAR(32) NOT NULL,
    color VARCHAR(32) NOT NULL,
    year  DATE NOT NULL,
    customer_id INT REFERENCES customers (id)

);

INSERT INTO customers (tabnum, name, surname, email, birth)
VALUES (100, 'Will', 'Smith', 'will@gmail.com', '25-09-1968'),
       (200, 'Leonardo', 'DiCaprio', 'leo@hotmail.com', '11-11-1974'),
       (300, 'Matthew', 'McConaughey', 'matthew@aol.com', '04-11-1969'),
       (400, 'Colin', 'Farrell', 'colin@yahoo.com', '31-05-1976');

INSERT INTO autos (model, color, year, customer_id)
VALUES ('X-trail', 'black', '2019-01-01', 1),
       ('Teana', 'black', '2016-01-01', 1),
       ('Qashqai', 'red', '2020-01-01', 2),
       ('Patrol', 'white', '2018-01-01', 3),
       ('Pathfinder', 'grey', '2019-01-01', 3);

INSERT INTO autos (model, color, year)
VALUES ('Murano', 'brown', '2020-01-01'),
       ('Almera', 'silver', '2019-01-01');