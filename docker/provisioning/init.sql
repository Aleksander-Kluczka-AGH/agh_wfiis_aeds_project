CREATE TABLE IF NOT EXISTS client
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(30),
    surname VARCHAR(30),
    email   VARCHAR(30),
    role    VARCHAR(30)
);

INSERT INTO client (id, name, surname, email, role)
VALUES (1, 'admin', 'admin', 'admin@admin.com', 'administrator')
ON CONFLICT (id)
DO NOTHING;

CREATE TABLE IF NOT EXISTS reservation
(
    id SERIAL PRIMARY KEY,
    client_id INTEGER,
    room_id INTEGER,
    start_date DATE,
    end_date DATE
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM   pg_constraint c
        JOIN   pg_namespace n ON n.oid = c.connamespace
        WHERE  n.nspname = 'public'
        AND    c.conname = 'fk_client_id'
    ) THEN
        -- Add constraint if it does not exist already
        ALTER TABLE    reservation
        ADD CONSTRAINT fk_client_id
        FOREIGN KEY    (client_id)
        REFERENCES     client(id);
    END IF;
END $$;
