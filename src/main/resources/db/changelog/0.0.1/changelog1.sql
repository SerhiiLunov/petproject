-- Create user table if not exists
CREATE TABLE IF NOT EXISTS "user" (
                                      id BIGSERIAL PRIMARY KEY,
                                      email VARCHAR(255) NOT NULL UNIQUE,
                                      password VARCHAR(255) NOT NULL,
                                      creation_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                                      modification_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Insert user with hashed password if not exists
INSERT INTO "user" (email, password)
VALUES ('user@gmail.com', '$2a$10$DOWSD8N4XS2Y/5TVjhyXSeHAdTsm8l6.e6O5u8sxEwAqFq/QL9Fpe') -- "password" hashed using BCrypt
ON CONFLICT (email) DO NOTHING;

-- Create a trigger function to update the modification_time column
CREATE OR REPLACE FUNCTION update_modification_time()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.modification_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Check the existence of a trigger before creating it
DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_trigger
            WHERE tgname = 'set_modification_time'
        ) THEN
-- Create the trigger to call the update_modification_time function before each update
CREATE TRIGGER set_modification_time
    BEFORE UPDATE ON "user"
    FOR EACH ROW
EXECUTE FUNCTION update_modification_time();
END IF;
END $$;


-- DROP TABLE "user" CASCADE ;
--
-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog1.sql';



