-- noinspection SqlNoDataSourceInspectionForFile

-- CREATE TABLE IF NOT EXISTS task_entry_details

ALTER TABLE current_properties
  ADD COLUMN qa_case TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN mark_up TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN amend_response TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN dcu_minister_signoff TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN home_sec_sign_off TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN hs_private_office_approval TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  RENAME COLUMN ukvi_private_office_approval To private_office_approval;