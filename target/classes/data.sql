-- src/main/resources/data.sql
INSERT INTO users (username, password, enabled) VALUES
('admin', '{noop}root', TRUE) -- {noop} is for plain text, use encoded password in real app!
ON CONFLICT (username) DO NOTHING;

INSERT INTO authorities (username, authority) VALUES
('admin', 'ROLE_USER')
ON CONFLICT DO NOTHING;