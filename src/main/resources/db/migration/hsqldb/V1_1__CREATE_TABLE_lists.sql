CREATE TABLE IF NOT EXISTS lists
(
    id          BIGSERIAL       PRIMARY KEY,
    reference   VARCHAR(20)     NOT NULL,

    CONSTRAINT list_name_idempotent UNIQUE (reference)
);

CREATE INDEX idx_reference ON lists (reference);

CREATE TABLE IF NOT EXISTS entities
(
    id                  BIGSERIAL       PRIMARY KEY,
    reference           VARCHAR(20)     NOT NULL,
    value               VARCHAR(50)     NOT NULL,
    list_id             INT,
    parent_entity_id    INT,

    CONSTRAINT entity_name_ref_idempotent UNIQUE (value, reference),
    CONSTRAINT fk_list_id FOREIGN KEY (list_id) REFERENCES lists(id),
    CONSTRAINT fk_parent_id FOREIGN KEY (parent_entity_id) REFERENCES entities(id)
);

CREATE INDEX idx_list_id ON entities (list_id);
CREATE INDEX idx_parent_id ON entities (parent_entity_id);

CREATE TABLE IF NOT EXISTS list_properties
(
    id          BIGSERIAL       PRIMARY KEY,
    entity_id   INT             NOT NULL,
    property    VARCHAR(20)     NOT NULL,
    value       TEXT,

    CONSTRAINT entity_id_idempotent UNIQUE (entity_id, property, value)
);

CREATE INDEX idx_property_id ON list_properties (id);
