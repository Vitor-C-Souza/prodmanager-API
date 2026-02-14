INSERT INTO users (id, email, password, username, role)
SELECT
    SYS_GUID(),
    'admin@prodmanager.com',
    '$2a$10$uytAFjZSce6pYRvNgewO6eRpBzpaoPZU4OeT66HLn1D1frZSrqeWS',
    'admin',
    'ADMIN'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@prodmanager.com');

COMMIT;