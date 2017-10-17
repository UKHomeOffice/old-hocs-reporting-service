CREATE TABLE IF NOT EXISTS lists
(
    id          BIGSERIAL       PRIMARY KEY,
    name VARCHAR(20) NOT NULL,

    CONSTRAINT list_name_idempotent UNIQUE (name)
);

CREATE INDEX idx_reference
    ON lists (name);

CREATE TABLE IF NOT EXISTS entities
(
    id                  BIGSERIAL       PRIMARY KEY,
    text             VARCHAR(20) NOT NULL,
    value            VARCHAR(50) NOT NULL,
    list_id          INT,
    parent_entity_id INT,

    CONSTRAINT entity_name_ref_idempotent UNIQUE (value, text),
    CONSTRAINT fk_list_id FOREIGN KEY (list_id) REFERENCES lists(id),
    CONSTRAINT fk_parent_id FOREIGN KEY (parent_entity_id) REFERENCES entities(id)
);

CREATE INDEX idx_list_id ON entities (list_id);
CREATE INDEX idx_parent_id ON entities (parent_entity_id);

CREATE TABLE IF NOT EXISTS properties
(
    id          BIGSERIAL       PRIMARY KEY,
    key       VARCHAR(20) NOT NULL,
    value     VARCHAR(50) NOT NULL,
    entity_id INT,

    CONSTRAINT entity_id_idempotent UNIQUE (entity_id, key, value),
    CONSTRAINT fk_entity_id FOREIGN KEY (entity_id) REFERENCES entities (id)
);

CREATE INDEX idx_entity_id
    ON properties (entity_id);
