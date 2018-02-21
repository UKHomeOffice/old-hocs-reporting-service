-- noinspection SqlNoDataSourceInspectionForFile

ALTER TABLE properties
ADD COLUMN exemptions TEXT DEFAULT NULL;

ALTER TABLE current_properties
ADD COLUMN exemptions TEXT DEFAULT NULL;

