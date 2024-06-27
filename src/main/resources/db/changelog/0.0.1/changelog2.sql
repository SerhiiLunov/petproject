

-- Insert role 'admin'
INSERT INTO role (name) VALUES ('admin') ON CONFLICT (name) DO NOTHING;

-- Insert permission 'user_creation'
INSERT INTO permission (name) VALUES ('user_creation') ON CONFLICT (name) DO NOTHING;

-- Add unique constraint to role_permission table
ALTER TABLE role_permission ADD CONSTRAINT role_permission_unique UNIQUE (role_id, permission_id);

-- Link role 'admin' with permission 'user_creation'
DO $$
    DECLARE
        v_role_id bigint;
        v_permission_id bigint;
    BEGIN
        SELECT id INTO v_role_id FROM role WHERE name = 'admin';
        SELECT id INTO v_permission_id FROM permission WHERE name = 'user_creation';

        INSERT INTO role_permission (role_id, permission_id)
        VALUES (v_role_id, v_permission_id)
        ON CONFLICT (role_id, permission_id) DO NOTHING;
    END $$;

-- Insert user with hashed password if not exists
INSERT INTO "users" (email, password)
VALUES ('user@gmail.com', 'password')
ON CONFLICT (email) DO NOTHING;

-- Insert JWT generation data if not exists
INSERT INTO technical (jwt_secret, jwt_issuer, jwt_expiration_ms)
VALUES ('your_jwt_secret_here', 'your_jwt_issuer_here', 3600000)
ON CONFLICT DO NOTHING;


-- DELETE FROM DATABASECHANGELOG;
-- DELETE FROM DATABASECHANGELOGLOCK;