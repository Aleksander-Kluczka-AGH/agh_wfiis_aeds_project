CREATE TABLE client
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(30),
    surname VARCHAR(30),
    email   VARCHAR(30),
    role    VARCHAR(30)
);

INSERT INTO client (name, surname, email, role) VALUES ('admin', 'admin', 'admin@admin.com', 'administrator');
