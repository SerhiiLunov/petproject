-- Create session table
CREATE TABLE IF NOT EXISTS session (
                                       id UUID PRIMARY KEY,
                                       token VARCHAR(255) NOT NULL,
                                       created_at TIMESTAMP NOT NULL,
                                       expires_at TIMESTAMP NOT NULL,
                                       user_id UUID NOT NULL,
                                       FOREIGN KEY (user_id) REFERENCES "user"(id),
                                       creation_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                                       modification_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog6.sql';
-- DROP TABLE "session" CASCADE ;