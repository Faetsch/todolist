CREATE TABLE trashbindeletionconfig
(
    id                SERIAL PRIMARY KEY,
    second            VARCHAR(255),
    minute            VARCHAR(255),
    hour              VARCHAR(255),
    dayOfWeek         VARCHAR(255),
    dayOfMonth        VARCHAR(255),
    month             VARCHAR(255),
    year              VARCHAR(255),
    activated         boolean,
    deletionThreshold TIMESTAMP
);