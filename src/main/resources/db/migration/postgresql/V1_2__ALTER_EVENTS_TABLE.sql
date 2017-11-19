-- noinspection SqlNoDataSourceInspectionForFile


ALTER TABLE events ALTER COLUMN before type JSONB USING detail::JSONB;
ALTER TABLE events ALTER COLUMN after type JSONB USING detail::JSONB;