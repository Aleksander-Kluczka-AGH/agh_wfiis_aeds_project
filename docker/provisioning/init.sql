CREATE TABLE client
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(30),
    surname VARCHAR(30),
    email   VARCHAR(30),
    role    VARCHAR(30)
);

INSERT INTO client (name, surname, email, role) VALUES ('admin', 'admin', 'admin@admin.com', 'administrator');

CREATE TABLE reservation
(
    id SERIAL PRIMARY KEY,
    client_id INTEGER,
    room_id INTEGER,
    start_date DATE,
    end_date DATE
);

ALTER TABLE reservation
    ADD CONSTRAINT fk_client_id
        FOREIGN KEY (client_id)
            REFERENCES client(id);
