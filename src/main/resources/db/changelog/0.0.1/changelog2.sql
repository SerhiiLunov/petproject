-- Create role table
CREATE TABLE IF NOT EXISTS role
(
    id                UUID PRIMARY KEY,
    name              VARCHAR(255) NOT NULL UNIQUE,
    creation_time     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    modification_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Insert role 'admin'
INSERT INTO role (id, name, creation_time, modification_time)
VALUES (uuid_generate_v4(), 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog2.sql';
-- DROP TABLE "role" CASCADE ;