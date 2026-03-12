CREATE TABLE IF NOT EXISTS t_letter_tags
(
    letter_tag_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at    DATETIME(6) NULL,
    updated_at    DATETIME(6) NULL,
    deleted_at    DATETIME(6) NULL,
    letter_id     BIGINT NOT NULL,
    tag           VARCHAR(50) NOT NULL,
    CONSTRAINT pk_t_letter_tags PRIMARY KEY (letter_tag_id)
);

CREATE INDEX idx_letter_tags_letter_id ON t_letter_tags (letter_id);
CREATE INDEX idx_letter_tags_tag ON t_letter_tags (tag);
