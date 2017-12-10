-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE IF NOT EXISTS events
(
    id              BIGSERIAL     PRIMARY KEY,
    msg_uuid        TEXT          NOT NULL,
    msg_timestamp   TIMESTAMP     NOT NULL,
    case_reference  TEXT          NOT NULL,
    before          TEXT          NOT NULL,
    after           TEXT          NOT NULL,

    CONSTRAINT event_id_idempotent UNIQUE (msg_uuid,msg_timestamp)
);

CREATE INDEX idx_events_case_reference ON events (case_reference);
CREATE INDEX idx_events_timestamp ON events (msg_timestamp);