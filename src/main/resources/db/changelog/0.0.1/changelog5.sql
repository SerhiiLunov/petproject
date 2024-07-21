-- Create user_role table
CREATE TABLE IF NOT EXISTS user_role (
                                         id BIGSERIAL PRIMARY KEY,
                                         user_id BIGINT NOT NULL,
                                         role_id BIGINT NOT NULL,
                                         FOREIGN KEY (user_id) REFERENCES "user"(id),
                                         FOREIGN KEY (role_id) REFERENCES role(id)
);

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog5.sql';