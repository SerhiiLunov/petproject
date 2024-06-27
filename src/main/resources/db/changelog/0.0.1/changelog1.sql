-- Create users table
CREATE TABLE IF NOT EXISTS users (
                                    id BIGSERIAL PRIMARY KEY,
                                    email VARCHAR(255) NOT NULL UNIQUE,
                                    password VARCHAR(255) NOT NULL
);

-- Create role table
CREATE TABLE IF NOT EXISTS role (
                                    id BIGSERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL UNIQUE
);

-- Create permission table
CREATE TABLE IF NOT EXISTS permission (
                                          id BIGSERIAL PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL UNIQUE
);

-- Create role_permission table
CREATE TABLE IF NOT EXISTS role_permission (
                                               id BIGSERIAL PRIMARY KEY,
                                               role_id BIGINT NOT NULL,
                                               permission_id BIGINT NOT NULL,
                                               FOREIGN KEY (role_id) REFERENCES role(id),
                                               FOREIGN KEY (permission_id) REFERENCES permission(id)
);

-- Create user_role table
CREATE TABLE IF NOT EXISTS user_role (
                                         id BIGSERIAL PRIMARY KEY,
                                         user_id BIGINT NOT NULL,
                                         role_id BIGINT NOT NULL,
                                         FOREIGN KEY (user_id) REFERENCES users(id),
                                         FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Create session table
CREATE TABLE IF NOT EXISTS session (
                                       id BIGSERIAL PRIMARY KEY,
                                       token VARCHAR(255) NOT NULL,
                                       created_at TIMESTAMP NOT NULL,
                                       expires_at TIMESTAMP NOT NULL,
                                       user_id BIGINT NOT NULL,
                                       FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create technical table
CREATE TABLE IF NOT EXISTS technical (
                                         id BIGSERIAL PRIMARY KEY,
                                         jwt_secret VARCHAR(255),
                                         jwt_issuer VARCHAR(255),
                                         jwt_expiration_ms INT
);

-- DROP TABLE IF EXISTS user_role CASCADE;
-- DROP TABLE IF EXISTS session CASCADE;
-- DROP TABLE IF EXISTS users CASCADE;
-- DROP TABLE IF EXISTS role CASCADE;
-- DROP TABLE IF EXISTS permission CASCADE;
-- DROP TABLE IF EXISTS role_permission CASCADE;
-- DROP TABLE IF EXISTS technical CASCADE;
--
-- DELETE FROM DATABASECHANGELOG;
-- DELETE FROM DATABASECHANGELOGLOCK;
