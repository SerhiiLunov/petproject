-- Create technical table
CREATE TABLE IF NOT EXISTS technical (
                                         id BIGSERIAL PRIMARY KEY,
                                         jwt_secret VARCHAR(255),
                                         jwt_issuer VARCHAR(255),
                                         jwt_expiration_ms INT
);

-- Insert JWT generation data if not exists
INSERT INTO technical (jwt_secret, jwt_issuer, jwt_expiration_ms)
VALUES ('your_jwt_secret_here', 'your_jwt_issuer_here', 3600000)
ON CONFLICT DO NOTHING;

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog7.sql';