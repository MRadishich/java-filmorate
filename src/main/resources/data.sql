INSERT INTO mpa_rating (name, description) VALUES ('G', 'У фильма нет возрастных ограничений');
INSERT INTO mpa_rating (name, description) VALUES ('PG', 'Детям рекомендуется смотреть фильм с родителями');
INSERT INTO mpa_rating (name, description) VALUES ('PG-13', 'Детям до 13 лет просмотр не желателен');
INSERT INTO mpa_rating (name, description) VALUES ('R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
INSERT INTO mpa_rating (name, description) VALUES ('NC-17', 'Лицам до 18 лет просмотр запрещён.');

INSERT INTO genres (name) VALUES ('Комедия');
INSERT INTO genres (name) VALUES ('Драма');
INSERT INTO genres (name) VALUES ('Мультфильм');
INSERT INTO genres (name) VALUES ('Триллер');
INSERT INTO genres (name) VALUES ('Документальный');
INSERT INTO genres (name) VALUES ('Боевик');

INSERT INTO friendship_status (id, name) VALUES (0, 'Не подтверждена');
INSERT INTO friendship_status (id, name) VALUES (1, 'Подтверждена');

