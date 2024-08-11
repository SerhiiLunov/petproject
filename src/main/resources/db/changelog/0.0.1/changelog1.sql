-- Create user table if not exists
CREATE TABLE IF NOT EXISTS "user"
(
    id                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email             VARCHAR(255) NOT NULL UNIQUE,
    password          VARCHAR(255) NOT NULL,
    creation_time     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    modification_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Insert user with hashed password if not exists
INSERT INTO "user" (id, email, password, creation_time, modification_time)
VALUES (uuid_generate_v4(), 'user@gmail.com', '$2a$10$DOWSD8N4XS2Y/5TVjhyXSeHAdTsm8l6.e6O5u8sxEwAqFq/QL9Fpe',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- DROP TABLE "user" CASCADE ;
-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog1.sql';



