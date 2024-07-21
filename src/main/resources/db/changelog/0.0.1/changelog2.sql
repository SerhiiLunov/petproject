-- Create role table
CREATE TABLE IF NOT EXISTS role (
                                    id BIGSERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL UNIQUE
);

-- Insert role 'admin'
INSERT INTO role (name) VALUES ('admin') ON CONFLICT (name) DO NOTHING;

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog2.sql';