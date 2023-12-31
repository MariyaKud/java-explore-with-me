DROP TABLE IF EXISTS categories, users, locations, compilation_of_events, requests, compilations, admin_comments, events CASCADE;

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     name VARCHAR(250) NOT NULL,
                                     email VARCHAR(254) NOT NULL,
                                     CONSTRAINT pk_user PRIMARY KEY (id),
                                     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
                                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                       name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations (
                                          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                          title VARCHAR(50) UNIQUE NOT NULL,
                                          pinned BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS locations (
                                      id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                      lat REAL NOT NULL,
                                      lon REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS events (
                                      id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                      category_id BIGINT NOT NULL,
                                      location_id BIGINT NOT NULL,
                                      initiator_id BIGINT NOT NULL,
                                      event_date timestamp,
                                      created_on timestamp,
                                      published_on timestamp,
                                      annotation VARCHAR(2000) NOT NULL,
                                      title VARCHAR(120) NOT NULL,
                                      state_event VARCHAR(10) NOT NULL,
                                      description VARCHAR(7000) NOT NULL,
                                      paid BOOLEAN NOT NULL,
                                      request_moderation BOOLEAN NOT NULL,
                                      participant_limit BIGINT NOT NULL,
                                      CONSTRAINT fk_events_to_categories FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE CASCADE,
                                      CONSTRAINT fk_events_to_locations FOREIGN KEY(location_id) REFERENCES locations(id) ON DELETE CASCADE,
                                      CONSTRAINT fk_events_to_users FOREIGN KEY(initiator_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests (
                                      id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                      event_id BIGINT NOT NULL,
                                      requester_id BIGINT NOT NULL,
                                      created timestamp,
                                      status VARCHAR(50) NOT NULL,
                                      CONSTRAINT fk_requests_to_events FOREIGN KEY(event_id) REFERENCES events(id) ON DELETE CASCADE,
                                      CONSTRAINT fk_requests_to_users FOREIGN KEY(requester_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compilation_of_events (
                                      event_id INTEGER REFERENCES events(id) ON DELETE CASCADE,
                                      compilation_id INTEGER REFERENCES compilations(id) ON DELETE CASCADE,
                                      CONSTRAINT compilations_events_pk PRIMARY KEY (event_id, compilation_id)
);

CREATE TABLE IF NOT EXISTS admin_comments (
                                      id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                      event_id BIGINT NOT NULL,
                                      text varchar(2000),
                                      created timestamp,
                                      state_comment VARCHAR(50) NOT NULL,
                                      CONSTRAINT fk_admin_comments_to_events FOREIGN KEY(event_id) REFERENCES events(id) ON DELETE CASCADE
);
