CREATE TABLE IF NOT EXISTS genres
(
    id   int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(100) NOT NULL,
    CONSTRAINT genres_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS mpa_rating
(
    id          int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    name        varchar(100) NOT NULL,
    description varchar(255) NOT NULL,
    CONSTRAINT mpa_rating_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS friendship_status
(
    id   int4 PRIMARY KEY NOT NULL,
    name varchar(100)     NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id       int8         NOT NULL GENERATED ALWAYS AS IDENTITY,
    email    varchar(255) NOT NULL,
    login    varchar(100) NOT NULL,
    name     varchar(255) NULL,
    birthday date         NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE UNIQUE INDEX users_unique ON users (email);

CREATE TABLE IF NOT EXISTS films
(
    id            int8         NOT NULL GENERATED ALWAYS AS IDENTITY,
    title         varchar(255) NOT NULL,
    description   varchar(200) NULL,
    release_date  date         NULL,
    duration      int4         NOT NULL,
    mpa_rating_id int4         NULL,
    CONSTRAINT films_pkey PRIMARY KEY (id),
    CONSTRAINT films_mpa_rating_id_fkey FOREIGN KEY (mpa_rating_id) REFERENCES mpa_rating (id)
);

CREATE TABLE IF NOT EXISTS friendship
(
    user_id   int8 NOT NULL,
    friend_id int8 NOT NULL,
    status    int4 NOT NULL,
    CONSTRAINT friendship_pk PRIMARY KEY (user_id, friend_id),
    CONSTRAINT friendship_friend_id_fkey FOREIGN KEY (friend_id) REFERENCES users (id),
    CONSTRAINT friendship_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT friendship_status_friendship_status_fk FOREIGN KEY (status) REFERENCES friendship_status (id)
);

CREATE TABLE IF NOT EXISTS likes
(
    film_id int8 NOT NULL,
    user_id int8 NOT NULL,
    CONSTRAINT likes_pk PRIMARY KEY (film_id, user_id),
    CONSTRAINT likes_film_id_fkey FOREIGN KEY (film_id) REFERENCES films (id),
    CONSTRAINT likes_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS films_genres
(
    film_id  int8 NOT NULL,
    genre_id int8 NOT NULL,
    CONSTRAINT film_genres_pk PRIMARY KEY (film_id, genre_id),
    CONSTRAINT film_genres_film_id_fkey FOREIGN KEY (film_id) REFERENCES films (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT film_genres_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES genres (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews
(
    id          int8    NOT NULL GENERATED ALWAYS AS IDENTITY,
    content     varchar NOT NULL,
    is_positive boolean NOT NULL,
    user_id     integer NOT NULL,
    film_id     integer NOT NULL,
    useful      integer DEFAULT 0,
    CONSTRAINT reviews_pk PRIMARY KEY (id),
    CONSTRAINT reviews_film_fk FOREIGN KEY (film_id) REFERENCES films (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT reviews_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS reviews_likes
(
    review_id int8    NOT NULL,
    user_id   integer NOT NULL,
    is_like   boolean NOT NULL,
    CONSTRAINT reviews_likes_pk PRIMARY KEY (review_id, user_id),
    CONSTRAINT reviews_likes_reviews_fk FOREIGN KEY (review_id) REFERENCES reviews (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT reviews_likes_users_fk FOREIGN KEY (user_id) REFERENCES users (id)
);
