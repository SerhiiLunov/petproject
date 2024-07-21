-- Create permission table
CREATE TABLE IF NOT EXISTS permission (
                                          id BIGSERIAL PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL UNIQUE
);

-- Insert permission 'user_creation'
INSERT INTO permission (name) VALUES ('user_creation') ON CONFLICT (name) DO NOTHING;

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog3.sql';