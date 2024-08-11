-- Create permission table
CREATE TABLE IF NOT EXISTS permission
(
    id                UUID PRIMARY KEY,
    name              VARCHAR(255) NOT NULL UNIQUE,
    creation_time     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    modification_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Insert permission 'user_creation'
INSERT INTO permission (id, name, creation_time, modification_time)
VALUES (uuid_generate_v4(), 'user_creation', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog3.sql';
-- DROP TABLE "permission" CASCADE ;