CREATE TABLE COURSE (
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
    NAME VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(1000) NOT NULL,
    CREATED_AT VARCHAR(60) NOT NULL,
    UPLOADED_AT VARCHAR(60) NOT NULL,
    PRIMARY KEY (ID)
);