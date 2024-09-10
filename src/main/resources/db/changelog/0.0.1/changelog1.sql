-- Create user table if not exists

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "user"
(
    id                          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    login                       VARCHAR(255) NOT NULL UNIQUE,
    password                    VARCHAR(255) NOT NULL,
    description                 VARCHAR(255),
    state                       VARCHAR(255),
    create_date_time            TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    last_modification_date_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Insert user with hashed password if not exists
INSERT INTO "user" (id, login, password, description, state, create_date_time, last_modification_date_time)
VALUES (uuid_generate_v4(), 'user@gmail.com', '$2a$10$DOWSD8N4XS2Y/5TVjhyXSeHAdTsm8l6.e6O5u8sxEwAqFq/QL9Fpe',
        'Test user description', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (login) DO NOTHING;

-- DROP TABLE "user" CASCADE ;
-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog1.sql';
-- DELETE FROM "user" WHERE login = 'lunevlife@gmail.com';



