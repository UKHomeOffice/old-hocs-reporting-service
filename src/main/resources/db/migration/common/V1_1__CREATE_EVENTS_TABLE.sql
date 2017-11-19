-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE IF NOT EXISTS events
(
    id              BIGSERIAL     PRIMARY KEY,
    uuid            TEXT          NOT NULL,
    timestamp       TIMESTAMP     NOT NULL,
    node_reference  TEXT          NOT NULL,
    case_reference  TEXT          NOT NULL,
    case_type       TEXT          NOT NULL,
    before          TEXT          NOT NULL,
    after           TEXT          NOT NULL,

    CONSTRAINT event_id_idempotent UNIQUE (uuid,case_reference)
);

CREATE INDEX idx_events_case_reference ON events (case_reference);
CREATE INDEX idx_events_timestamp ON events (timestamp);