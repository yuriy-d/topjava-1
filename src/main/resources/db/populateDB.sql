DELETE FROM user_roles;
DELETE FROM users;
DELETE from meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (userid, dateTime, description, calories) VALUES
  (100000, '2017-10-23 09:23:54', 'breakfast', 500),
  (100000, '2017-10-23 13:22:54', 'dinner', 1000),
  (100000, '2017-10-23 20:21:54', 'supper', 510),
  (100001, '2017-10-23 09:13:54', 'breakfast', 500),
  (100001, '2017-10-23 13:12:54', 'dinner', 1000),
  (100001, '2017-10-23 20:11:54', 'supper', 500),
  (100000, '2017-10-24 09:20:40', 'breakfast', 400),
  (100000, '2017-10-24 12:59:50', 'dinner', 990),
  (100000, '2017-10-24 19:43:44', 'supper', 410),
  (100001, '2017-10-24 09:10:40', 'breakfast', 400),
  (100001, '2017-10-24 12:19:50', 'dinner', 990),
  (100001, '2017-10-24 19:13:44', 'supper', 650);