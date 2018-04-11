-- noinspection SqlNoDataSourceInspectionForFile

-- CREATE TABLE IF NOT EXISTS task_entry_details

ALTER TABLE current_properties
  ADD COLUMN create_case TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN draft_response TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN qa_review TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN ukvi_cqt_approval TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN ukvi_private_office_approval TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN foi_scs_approval TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN foi_press_office_review TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN foi_spads_approval TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN foi_foi_minister_signoff TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN dispatch_response TIMESTAMP DEFAULT NULL;
ALTER TABLE current_properties
  ADD COLUMN completed TIMESTAMP DEFAULT NULL;
