-- Create technical table
CREATE TABLE IF NOT EXISTS technical (
                                         id UUID PRIMARY KEY,
                                         jwt_secret VARCHAR(255),
                                         jwt_issuer VARCHAR(255),
                                         jwt_expiration_ms INT
);

-- Insert JWT generation data if not exists
INSERT INTO technical (id, jwt_secret, jwt_issuer, jwt_expiration_ms)
VALUES (uuid_generate_v4(), 'your_jwt_secret_here', 'your_jwt_issuer_here', 3600000)
ON CONFLICT DO NOTHING;

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog7.sql';
-- DROP TABLE "technical" CASCADE ;