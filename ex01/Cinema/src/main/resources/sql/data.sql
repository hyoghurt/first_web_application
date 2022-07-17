DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

-- Brad password - brad
-- Quentin password - quentin
INSERT INTO users (first_name, last_name, phone, password)
VALUES
('Brad', 'Pitt', '8900121212', '$2a$10$X1h0LNQMtjlODWPYL8wI1uuLOU66AZtuHBzKMJZn4Tr5XkcEdVcRe'),
('Quentin', 'Tarantino', '8900131313', '$2a$10$XZta7wNjHLhvEuqldYBZfuxXRtf8PRyYq.AhiHJ4lvavsdcPovqtO');
