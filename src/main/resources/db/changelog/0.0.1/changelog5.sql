-- Create user_role table
CREATE TABLE IF NOT EXISTS user_role (
                                         id UUID PRIMARY KEY,
                                         user_id UUID NOT NULL,
                                         role_id UUID NOT NULL,
                                         FOREIGN KEY (user_id) REFERENCES "user"(id),
                                         FOREIGN KEY (role_id) REFERENCES role(id)
);

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog5.sql';
-- DROP TABLE "user_role" CASCADE ;
