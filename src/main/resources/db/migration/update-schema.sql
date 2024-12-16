CREATE TABLE books
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255) NOT NULL,
    author        VARCHAR(255) NULL,
    isbn          VARCHAR(255) NULL,
    price         DECIMAL NULL,
    `description` VARCHAR(255) NULL,
    cover_image   VARCHAR(255) NULL,
    is_deleted    BIT(1)       NOT NULL,
    CONSTRAINT pk_books PRIMARY KEY (id)
);

ALTER TABLE books
    ADD CONSTRAINT uc_books_isbn UNIQUE (isbn);