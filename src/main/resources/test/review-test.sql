INSERT INTO users (email, login, name, birthday)
VALUES ('email1@email.ru', 'SuperJoe', 'Joe', '2000-02-01');

INSERT INTO users (email, login, name, birthday)
VALUES ('email2@email.ru', 'SuperBob', 'Bob', '2000-02-01');

INSERT INTO users (email, login, name, birthday)
VALUES ('email3@email.ru', 'SuperDen', 'Den', '2000-02-01');

INSERT INTO films (title, description, release_date, duration, mpa_rating_id)
VALUES ('First film', 'First film', '1998-03-30', 120, 1);

INSERT INTO films (title, description, release_date, duration, mpa_rating_id)
VALUES ('Second film', 'Second film', '1999-09-10', 180, 2);

INSERT INTO films (title, description, release_date, duration, mpa_rating_id)
VALUES ('Last film', 'Last film', '2009-09-09', 154, 1);

INSERT INTO reviews (content, is_positive, user_id, film_id, useful)
VALUES ('Good film', true, 1, 1, 1);

INSERT INTO reviews (content, is_positive, user_id, film_id, useful)
VALUES ('Bad film', false, 2, 2, 0);

INSERT INTO REVIEWS_LIKES (review_id, user_id, is_like)
VALUES (1, 2, true);