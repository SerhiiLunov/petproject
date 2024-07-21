-- Create role_permission table
CREATE TABLE IF NOT EXISTS role_permission (
                                               id BIGSERIAL PRIMARY KEY,
                                               role_id BIGINT NOT NULL,
                                               permission_id BIGINT NOT NULL,
                                               FOREIGN KEY (role_id) REFERENCES role(id),
                                               FOREIGN KEY (permission_id) REFERENCES permission(id)
);

-- Check if unique constraint exists before adding it
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'role_permission_unique') THEN
            ALTER TABLE role_permission ADD CONSTRAINT role_permission_unique UNIQUE (role_id, permission_id);
        END IF;
    END $$;


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

-- DELETE FROM DATABASECHANGELOG WHERE filename = 'db/changelog/0.0.1/changelog4.sql';