CREATE SEQUENCE room_SEQ START 1 INCREMENT BY 1;

CREATE TABLE room (
    id BIGINT,
    name TEXT NOT NULL
);

CREATE SEQUENCE apparatus_SEQ START 1 INCREMENT BY 1;

CREATE TABLE apparatus (
    id BIGINT,
    extension_number BIGINT NOT NULL, 
    room_id BIGINT NOT NULL,
    operator_name TEXT NOT NULL
);