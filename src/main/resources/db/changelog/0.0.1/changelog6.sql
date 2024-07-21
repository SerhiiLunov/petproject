-- Create session table
CREATE TABLE IF NOT EXISTS session (
                                       id BIGSERIAL PRIMARY KEY,
                                       token VARCHAR(255) NOT NULL,
                                       created_at TIMESTAMP NOT NULL,
                                       expires_at TIMESTAMP NOT NULL,
                                       user_id BIGINT NOT NULL,
                                       FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog6.sql';