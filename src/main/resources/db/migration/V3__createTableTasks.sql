CREATE TABLE task
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR(255),
    name        VARCHAR(255),
    version     int,
    done        boolean,
    deleted     boolean,
    priority    VARCHAR(255),
    deadline    TIMESTAMP,
    deletedAt   TIMESTAMP,
    owner_id    int,

    CONSTRAINT fk_customer
        FOREIGN KEY(owner_id)
            REFERENCES userlogindetails(id)
);