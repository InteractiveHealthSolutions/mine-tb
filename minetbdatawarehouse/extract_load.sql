-- active_list
SELECT `active_list`.`active_list_id`,`active_list`.`active_list_type_id`,`active_list`.`person_id`,`active_list`.`concept_id`,`active_list`.`start_obs_id`,`active_list`.`stop_obs_id`,`active_list`.`start_date`,`active_list`.`end_date`,`active_list`.`comments`,`active_list`.`creator`,`active_list`.`date_created`,`active_list`.`voided`,`active_list`.`voided_by`,`active_list`.`date_voided`,`active_list`.`void_reason`,`active_list`.`uuid`FROM `active_list`;
INSERT INTO active_list` (`active_list_id`, `active_list_type_id`, `person_id`, `concept_id`, `start_obs_id`, `stop_obs_id`, `start_date`, `end_date`, `comments`, `creator`, `date_created`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

-- active_list_allergy
SELECT `active_list_allergy`.`active_list_id`,`active_list_allergy`.`allergy_type`,`active_list_allergy`.`reaction_concept_id`,`active_list_allergy`.`severity`FROM `active_list_allergy`;
INSERT INTO `active_list_allergy` (`active_list_id`, `allergy_type`, `reaction_concept_id`, `severity`) VALUES (?, ?, ?, ?); 

-- active_list_problem
SELECT `active_list_problem`.`active_list_id`,`active_list_problem`.`status`,`active_list_problem`.`sort_weight`FROM `active_list_problem`;
INSERT INTO `active_list_problem` (`active_list_id`, `status`, `sort_weight`) VALUES (?, ?, ?);

-- active_list_type
SELECT `active_list_type`.`active_list_type_id`, `active_list_type`.`name`, `active_list_type`.`description`, `active_list_type`.`creator`, `active_list_type`.`date_created`, `active_list_type`.`retired`, `active_list_type`.`retired_by`, `active_list_type`.`date_retired`, `active_list_type`.`retire_reason`, `active_list_type`.`uuid` FROM `active_list_type`; 
INSERT INTO `active_list_type` (`active_list_type_id`, `name`, `description`, `creator`, `date_created`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- calculation_registration
SELECT `calculation_registration`.`calculation_registration_id`,`calculation_registration`.`token`,`calculation_registration`.`provider_class_name`,`calculation_registration`.`calculation_name`,`calculation_registration`.`configuration`,`calculation_registration`.`uuid` FROM `calculation_registration`;
INSERT INTO `calculation_registration` (`calculation_registration_id`, `token`, `provider_class_name`, `calculation_name`, `configuration`, `uuid`) VALUES (?, ?, ?, ?, ?, ?);

-- clob_datatype_storage
SELECT `clob_datatype_storage`.`id`,`clob_datatype_storage`.`uuid`,`clob_datatype_storage`.`value` FROM `clob_datatype_storage`;
INSERT INTO `clob_datatype_storage` (`id`, `uuid`, `value`) VALUES (?, ?, ?); 

-- concept
SELECT `concept`.`concept_id`,`concept`.`retired`,`concept`.`short_name`,`concept`.`description`,`concept`.`form_text`,`concept`.`datatype_id`,`concept`.`class_id`,`concept`.`is_set`,`concept`.`creator`,`concept`.`date_created`,`concept`.`version`,`concept`.`changed_by`,`concept`.`date_changed`,`concept`.`retired_by`,`concept`.`date_retired`, `concept`.`retire_reason`, `concept`.`uuid` FROM `concept`;
INSERT INTO `concept` (`concept_id`, `retired`, `short_name`, `description`, `form_text`, `datatype_id`, `class_id`, `is_set`, `creator`, `date_created`, `version`, `changed_by`, `date_changed`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_answer
SELECT `concept_answer`.`concept_answer_id`, `concept_answer`.`concept_id`,`concept_answer`.`answer_concept`,`concept_answer`.`answer_drug`,`concept_answer`.`creator`,`concept_answer`.`date_created`,`concept_answer`.`sort_weight`,`concept_answer`.`uuid`FROM `concept_answer`;
INSERT INTO `concept_answer` (`concept_answer_id`, `concept_id`, `answer_concept`, `answer_drug`, `creator`, `date_created`, `sort_weight`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_class
SELECT `concept_class`.`concept_class_id`,`concept_class`.`name`,`concept_class`.`description`,`concept_class`.`creator`,`concept_class`.`date_created`,`concept_class`.`retired`,`concept_class`.`retired_by`,`concept_class`.`date_retired`,`concept_class`.`retire_reason`,`concept_class`.`uuid`FROM `concept_class`;
INSERT INTO `concept_class` (`concept_class_id`, `name`, `description`, `creator`, `date_created`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_complex
SELECT `concept_complex`.`concept_id`,`concept_complex`.`handler` FROM `concept_complex`;
INSERT INTO `concept_complex` (`concept_id`, `handler`) VALUES (?, ?); 

-- concept_datatype
SELECT `concept_datatype`.`concept_datatype_id`,`concept_datatype`.`name`,`concept_datatype`.`hl7_abbreviation`,`concept_datatype`.`description`,`concept_datatype`.`creator`,`concept_datatype`.`date_created`,`concept_datatype`.`retired`,`concept_datatype`.`retired_by`,`concept_datatype`.`date_retired`,`concept_datatype`.`retire_reason`,`concept_datatype`.`uuid` FROM `concept_datatype`;
INSERT INTO `concept_datatype` (`concept_datatype_id`, `name`, `hl7_abbreviation`, `description`, `creator`, `date_created`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_description
SELECT `concept_description`.`concept_description_id`,`concept_description`.`concept_id`,`concept_description`.`description`,`concept_description`.`locale`,`concept_description`.`creator`,`concept_description`.`date_created`,`concept_description`.`changed_by`,`concept_description`.`date_changed`,`concept_description`.`uuid` FROM `concept_description`;
INSERT INTO `concept_description` (`concept_description_id`, `concept_id`, `description`, `locale`, `creator`, `date_created`, `changed_by`, `date_changed`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_map_type
SELECT `concept_map_type`.`concept_map_type_id`,`concept_map_type`.`name`,`concept_map_type`.`description`,`concept_map_type`.`creator`,`concept_map_type`.`date_created`,`concept_map_type`.`changed_by`,`concept_map_type`.`date_changed`,`concept_map_type`.`is_hidden`,`concept_map_type`.`retired`,`concept_map_type`.`retired_by`,`concept_map_type`.`date_retired`,`concept_map_type`.`retire_reason`,`concept_map_type`.`uuid` FROM `concept_map_type`;
INSERT INTO `concept_map_type` (`concept_map_type_id`, `name`, `description`, `creator`, `date_created`, `changed_by`, `date_changed`, `is_hidden`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_name
SELECT `concept_name`.`concept_name_id`,`concept_name`.`concept_id`,`concept_name`.`name`,`concept_name`.`locale`,`concept_name`.`locale_preferred`,`concept_name`.`creator`,`concept_name`.`date_created`,`concept_name`.`concept_name_type`,`concept_name`.`voided`,`concept_name`.`voided_by`,`concept_name`.`date_voided`,`concept_name`.`void_reason`,`concept_name`.`uuid`FROM `concept_name`;
INSERT INTO `concept_name` (`concept_name_id`, `concept_id`, `name`, `locale`, `locale_preferred`, `creator`, `date_created`, `concept_name_type`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_name_tag
SELECT `concept_name_tag`.`concept_name_tag_id`,`concept_name_tag`.`tag`,`concept_name_tag`.`description`,`concept_name_tag`.`creator`,`concept_name_tag`.`date_created`,`concept_name_tag`.`voided`,`concept_name_tag`.`voided_by`,`concept_name_tag`.`date_voided`,`concept_name_tag`.`void_reason`,`concept_name_tag`.`uuid`FROM `concept_name_tag`;
INSERT INTO `concept_name_tag` (`concept_name_tag_id`, `tag`, `description`, `creator`, `date_created`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_name_tag_map
SELECT `concept_name_tag_map`.`concept_name_id`,`concept_name_tag_map`.`concept_name_tag_id` FROM `concept_name_tag_map`;
INSERT INTO `concept_name_tag_map` (`concept_name_id`, `concept_name_tag_id`) VALUES (?, ?); 

-- concept_numeric
SELECT `concept_numeric`.`concept_id`,`concept_numeric`.`hi_absolute`,`concept_numeric`.`hi_critical`,`concept_numeric`.`hi_normal`,`concept_numeric`.`low_absolute`,`concept_numeric`.`low_critical`,`concept_numeric`.`low_normal`,`concept_numeric`.`units`,`concept_numeric`.`precise`FROM `concept_numeric`;
INSERT INTO `concept_numeric` (`concept_id`, `hi_absolute`, `hi_critical`, `hi_normal`, `low_absolute`, `low_critical`, `low_normal`, `units`, `precise`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_proposal
SELECT `concept_proposal`.`concept_proposal_id`,`concept_proposal`.`concept_id`,`concept_proposal`.`encounter_id`,`concept_proposal`.`original_text`,`concept_proposal`.`final_text`,`concept_proposal`.`obs_id`,`concept_proposal`.`obs_concept_id`,`concept_proposal`.`state`,`concept_proposal`.`comments`,`concept_proposal`.`creator`,`concept_proposal`.`date_created`,`concept_proposal`.`changed_by`,`concept_proposal`.`date_changed`,`concept_proposal`.`locale`,`concept_proposal`.`uuid` FROM `concept_proposal`;
INSERT INTO `concept_proposal` (`concept_proposal_id`, `concept_id`, `encounter_id`, `original_text`, `final_text`, `obs_id`, `obs_concept_id`, `state`, `comments`, `creator`, `date_created`, `changed_by`, `date_changed`, `locale`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_proposal_tag_map
SELECT `concept_proposal_tag_map`.`concept_proposal_id`, `concept_proposal_tag_map`.`concept_name_tag_id` FROM `concept_proposal_tag_map`;
INSERT INTO `concept_proposal_tag_map` (`concept_proposal_id`, `concept_name_tag_id`) VALUES (?, ?); 

-- concept_reference_map
SELECT `concept_reference_map`.`concept_map_id`, `concept_reference_map`.`concept_reference_term_id`,`concept_reference_map`.`concept_map_type_id`,`concept_reference_map`.`creator`,`concept_reference_map`.`date_created`,`concept_reference_map`.`concept_id`,`concept_reference_map`.`changed_by`,`concept_reference_map`.`date_changed`,`concept_reference_map`.`uuid`FROM `concept_reference_map`;
INSERT INTO `concept_reference_map` (`concept_map_id`, `concept_reference_term_id`, `concept_map_type_id`, `creator`, `date_created`, `concept_id`, `changed_by`, `date_changed`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_reference_source
SELECT `concept_reference_source`.`concept_source_id`,`concept_reference_source`.`name`,`concept_reference_source`.`description`,`concept_reference_source`.`hl7_code`,`concept_reference_source`.`creator`,`concept_reference_source`.`date_created`,`concept_reference_source`.`retired`,`concept_reference_source`.`retired_by`,`concept_reference_source`.`date_retired`,`concept_reference_source`.`retire_reason`,`concept_reference_source`.`uuid`FROM `concept_reference_source`;
INSERT INTO `concept_reference_source` (`concept_source_id`, `name`, `description`, `hl7_code`, `creator`, `date_created`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_reference_term
SELECT `concept_reference_term`.`concept_reference_term_id`,`concept_reference_term`.`concept_source_id`,`concept_reference_term`.`name`,`concept_reference_term`.`code`,`concept_reference_term`.`version`,`concept_reference_term`.`description`,`concept_reference_term`.`creator`,`concept_reference_term`.`date_created`,`concept_reference_term`.`date_changed`,`concept_reference_term`.`changed_by`,`concept_reference_term`.`retired`,`concept_reference_term`.`retired_by`,`concept_reference_term`.`date_retired`,`concept_reference_term`.`retire_reason`,`concept_reference_term`.`uuid` FROM `concept_reference_term`;
INSERT INTO `concept_reference_term` (`concept_reference_term_id`, `concept_source_id`, `name`, `code`, `version`, `description`, `creator`, `date_created`, `date_changed`, `changed_by`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_reference_term_map
SELECT `concept_reference_term_map`.`concept_reference_term_map_id`,`concept_reference_term_map`.`term_a_id`,`concept_reference_term_map`.`term_b_id`,`concept_reference_term_map`.`a_is_to_b_id`,`concept_reference_term_map`.`creator`,`concept_reference_term_map`.`date_created`,`concept_reference_term_map`.`changed_by`,`concept_reference_term_map`.`date_changed`,`concept_reference_term_map`.`uuid` FROM `concept_reference_term_map`;
INSERT INTO `concept_reference_term_map` (`concept_reference_term_map_id`, `term_a_id`, `term_b_id`, `a_is_to_b_id`, `creator`, `date_created`, `changed_by`, `date_changed`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- concept_set
SELECT `concept_set`.`concept_set_id`,`concept_set`.`concept_id`,`concept_set`.`concept_set`,`concept_set`.`sort_weight`,`concept_set`.`creator`,`concept_set`.`date_created`,`concept_set`.`uuid` FROM `concept_set`;
INSERT INTO `concept_set` (`concept_set_id`, `concept_id`, `concept_set`, `sort_weight`, `creator`, `date_created`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?); 

-- concept_set_derived
SELECT `concept_set_derived`.`concept_id`,`concept_set_derived`.`concept_set`,`concept_set_derived`.`sort_weight` FROM `concept_set_derived`;
INSERT INTO `concept_set_derived` (`concept_id`, `concept_set`, `sort_weight`) VALUES (?, ?, ?); 

-- concept_state_conversion
SELECT `concept_state_conversion`.`concept_state_conversion_id`,`concept_state_conversion`.`concept_id`,`concept_state_conversion`.`program_workflow_id`,`concept_state_conversion`.`program_workflow_state_id`,`concept_state_conversion`.`uuid` FROM `concept_state_conversion`;
INSERT INTO `concept_state_conversion` (`concept_state_conversion_id`, `concept_id`, `program_workflow_id`, `program_workflow_state_id`, `uuid`) VALUES (?, ?, ?, ?, ?); 

-- concepy_stop_word
SELECT `concept_stop_word`.`concept_stop_word_id`,`concept_stop_word`.`word`,`concept_stop_word`.`locale`,`concept_stop_word`.`uuid`FROM `concept_stop_word`;
INSERT INTO `concept_stop_word` (`concept_stop_word_id`, `word`, `locale`, `uuid`) VALUES (?, ?, ?, ?); 

-- concept_word
SELECT `concept_word`.`concept_word_id`,`concept_word`.`concept_id`,`concept_word`.`word`,`concept_word`.`locale`,`concept_word`.`concept_name_id`,`concept_word`.`weight`FROM `concept_word`;
INSERT INTO `concept_word` (`concept_word_id`, `concept_id`, `word`, `locale`, `concept_name_id`, `weight`) VALUES (?, ?, ?, ?, ?, ?); 

-- drug
SELECT `drug`.`drug_id`,`drug`.`concept_id`,`drug`.`name`,`drug`.`combination`,`drug`.`dosage_form`,`drug`.`dose_strength`,`drug`.`maximum_daily_dose`,`drug`.`minimum_daily_dose`,`drug`.`route`,`drug`.`units`,`drug`.`creator`,`drug`.`date_created`,`drug`.`retired`,`drug`.`changed_by`,`drug`.`date_changed`,`drug`.`retired_by`,`drug`.`date_retired`,`drug`.`retire_reason`,`drug`.`uuid` FROM `drug`;
INSERT INTO `drug` (`drug_id`, `concept_id`, `name`, `combination`, `dosage_form`, `dose_strength`, `maximum_daily_dose`, `minimum_daily_dose`, `route`, `units`, `creator`, `date_created`, `retired`, `changed_by`, `date_changed`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- drug_ingredient
SELECT `drug_ingredient`.`concept_id`,`drug_ingredient`.`ingredient_id` FROM `drug_ingredient`;
INSERT INTO `drug_ingredient` (`concept_id`, `ingredient_id`) VALUES (?, ?); 

-- drug_order
SELECT `drug_order`.`order_id`,`drug_order`.`drug_inventory_id`,`drug_order`.`dose`,`drug_order`.`equivalent_daily_dose`,`drug_order`.`units`,`drug_order`.`frequency`,`drug_order`.`prn`,`drug_order`.`complex`,`drug_order`.`quantity`FROM `drug_order`;
INSERT INTO `drug_order` (`order_id`, `drug_inventory_id`, `dose`, `equivalent_daily_dose`, `units`, `frequency`, `prn`, `complex`, `quantity`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- encounter
SELECT `encounter`.`encounter_id`,`encounter`.`encounter_type`,`encounter`.`patient_id`,`encounter`.`location_id`,`encounter`.`form_id`,`encounter`.`encounter_datetime`,`encounter`.`creator`,`encounter`.`date_created`,`encounter`.`voided`,`encounter`.`voided_by`,`encounter`.`date_voided`,`encounter`.`void_reason`,`encounter`.`changed_by`,`encounter`.`date_changed`,`encounter`.`visit_id`,`encounter`.`uuid` FROM `encounter`;
INSERT INTO `encounter` (`encounter_id`, `encounter_type`, `patient_id`, `location_id`, `form_id`, `encounter_datetime`, `creator`, `date_created`, `voided`, `voided_by`, `date_voided`, `void_reason`, `changed_by`, `date_changed`, `visit_id`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- encounter_provider
SELECT `encounter_provider`.`encounter_provider_id`,`encounter_provider`.`encounter_id`,`encounter_provider`.`provider_id`,`encounter_provider`.`encounter_role_id`,`encounter_provider`.`creator`,`encounter_provider`.`date_created`,`encounter_provider`.`changed_by`,`encounter_provider`.`date_changed`,`encounter_provider`.`voided`,`encounter_provider`.`date_voided`,`encounter_provider`.`voided_by`,`encounter_provider`.`void_reason`,`encounter_provider`.`uuid` FROM `encounter_provider`;
INSERT INTO `encounter_provider` (`encounter_provider_id`, `encounter_id`, `provider_id`, `encounter_role_id`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `date_voided`, `voided_by`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- encounter_role
SELECT `encounter_role`.`encounter_role_id`,`encounter_role`.`name`,`encounter_role`.`description`,`encounter_role`.`creator`,`encounter_role`.`date_created`,`encounter_role`.`changed_by`,`encounter_role`.`date_changed`,`encounter_role`.`retired`,`encounter_role`.`retired_by`,`encounter_role`.`date_retired`,`encounter_role`.`retire_reason`,`encounter_role`.`uuid` FROM `encounter_role`;
INSERT INTO `encounter_role` (`encounter_role_id`, `name`, `description`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- encounter_type
SELECT `encounter_type`.`encounter_type_id`,`encounter_type`.`name`,`encounter_type`.`description`,`encounter_type`.`creator`,`encounter_type`.`date_created`,`encounter_type`.`retired`,`encounter_type`.`retired_by`,`encounter_type`.`date_retired`,`encounter_type`.`retire_reason`,`encounter_type`.`uuid` FROM `encounter_type`;
INSERT INTO `encounter_type` (`encounter_type_id`, `name`, `description`, `creator`, `date_created`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- field
SELECT `field`.`field_id`,`field`.`name`,`field`.`description`,`field`.`field_type`,`field`.`concept_id`,`field`.`table_name`,`field`.`attribute_name`,`field`.`default_value`,`field`.`select_multiple`,`field`.`creator`,`field`.`date_created`,`field`.`changed_by`,`field`.`date_changed`,`field`.`retired`,`field`.`retired_by`,`field`.`date_retired`,`field`.`retire_reason`,`field`.`uuid` FROM `field`;
INSERT INTO `field` (`field_id`, `name`, `description`, `field_type`, `concept_id`, `table_name`, `attribute_name`, `default_value`, `select_multiple`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?); 

-- field_answer
SELECT `field_answer`.`field_id`,`field_answer`.`answer_id`,`field_answer`.`creator`,`field_answer`.`date_created`,`field_answer`.`uuid` FROM `field_answer`;
INSERT INTO `field_answer` (`field_id`, `answer_id`, `creator`, `date_created`, `uuid`) VALUES (?, ?, ?, ?, ?); 

-- field_type
SELECT `field_type`.`field_type_id`,`field_type`.`name`,`field_type`.`description`,`field_type`.`is_set`,`field_type`.`creator`,`field_type`.`date_created`,`field_type`.`uuid`FROM `field_type`;
INSERT INTO `field_type` (`field_type_id`, `name`, `description`, `is_set`, `creator`, `date_created`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?); 

-- form
SELECT `form`.`form_id`,`form`.`name`,`form`.`version`,`form`.`build`,`form`.`published`,`form`.`xslt`,`form`.`template`,`form`.`description`,`form`.`encounter_type`,`form`.`creator`,`form`.`date_created`,`form`.`changed_by`,`form`.`date_changed`,`form`.`retired`,`form`.`retired_by`,`form`.`date_retired`,`form`.`retired_reason`,`form`.`uuid` FROM `form`;
INSERT INTO `form` (`form_id`, `name`, `version`, `build`, `published`, `xslt`, `template`, `description`, `encounter_type`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retired_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- form_field
SELECT `form_field`.`form_field_id`,`form_field`.`form_id`,`form_field`.`field_id`,`form_field`.`field_number`,`form_field`.`field_part`,`form_field`.`page_number`,`form_field`.`parent_form_field`,`form_field`.`min_occurs`,`form_field`.`max_occurs`,`form_field`.`required`,`form_field`.`changed_by`,`form_field`.`date_changed`,`form_field`.`creator`,`form_field`.`date_created`,`form_field`.`sort_weight`,`form_field`.`uuid`FROM `form_field`;
INSERT INTO `form_field` (`form_field_id`, `form_id`, `field_id`, `field_number`, `field_part`, `page_number`, `parent_form_field`, `min_occurs`, `max_occurs`, `required`, `changed_by`, `date_changed`, `creator`, `date_created`, `sort_weight`, `uuid`) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- form_resource
SELECT `form_resource`.`form_resource_id`,`form_resource`.`form_id`,`form_resource`.`name`,`form_resource`.`value_reference`,`form_resource`.`datatype`,`form_resource`.`datatype_config`,`form_resource`.`preferred_handler`,`form_resource`.`handler_config`,`form_resource`.`uuid`FROM `form_resource`;
INSERT INTO `form_resource` (`form_resource_id`, `form_id`, `name`, `value_reference`, `datatype`, `datatype_config`, `preferred_handler`, `handler_config`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- global_property
SELECT `global_property`.`property`,`global_property`.`property_value`,`global_property`.`description`,`global_property`.`uuid`,`global_property`.`datatype`,`global_property`.`datatype_config`,`global_property`.`preferred_handler`,`global_property`.`handler_config` FROM `global_property`;
INSERT INTO `global_property` (`property`, `property_value`, `description`, `uuid`, `datatype`, `datatype_config`, `preferred_handler`, `handler_config`) VALUES (?, ?, ?, ?, ?, ?, ?, ?); 

-- hl7_in_archive
SELECT `hl7_in_archive`.`hl7_in_archive_id`,`hl7_in_archive`.`hl7_source`,`hl7_in_archive`.`hl7_source_key`,`hl7_in_archive`.`hl7_data`,`hl7_in_archive`.`date_created`,`hl7_in_archive`.`message_state`,`hl7_in_archive`.`uuid` FROM `hl7_in_archive`;
INSERT INTO `hl7_in_archive` (`hl7_in_archive_id`, `hl7_source`, `hl7_source_key`, `hl7_data`, `date_created`, `message_state`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?); 

-- hl7_in_error
SELECT `hl7_in_error`.`hl7_in_error_id`,`hl7_in_error`.`hl7_source`,`hl7_in_error`.`hl7_source_key`,`hl7_in_error`.`hl7_data`,`hl7_in_error`.`error`,`hl7_in_error`.`error_details`,`hl7_in_error`.`date_created`,`hl7_in_error`.`uuid` FROM `hl7_in_error`;
INSERT INTO `hl7_in_error` (`hl7_in_error_id`, `hl7_source`, `hl7_source_key`, `hl7_data`, `error`, `error_details`, `date_created`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?); 

-- hl7_in_queue
SELECT `hl7_in_queue`.`hl7_in_queue_id`,`hl7_in_queue`.`hl7_source`,`hl7_in_queue`.`hl7_source_key`,`hl7_in_queue`.`hl7_data`,`hl7_in_queue`.`message_state`,`hl7_in_queue`.`date_processed`,`hl7_in_queue`.`error_msg`,`hl7_in_queue`.`date_created`,`hl7_in_queue`.`uuid` FROM `hl7_in_queue`;
INSERT INTO `hl7_in_queue` (`hl7_in_queue_id`, `hl7_source`, `hl7_source_key`, `hl7_data`, `message_state`, `date_processed`, `error_msg`, `date_created`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- hl7_in_source
SELECT `hl7_source`.`hl7_source_id`,`hl7_source`.`name`,`hl7_source`.`description`,`hl7_source`.`creator`,`hl7_source`.`date_created`,`hl7_source`.`uuid` FROM `hl7_source`;
INSERT INTO `hl7_source` (`hl7_source_id`, `name`, `description`, `creator`, `date_created`, `uuid`) VALUES (?, ?, ?, ?, ?, ?); 

-- location
SELECT `location`.`location_id`,`location`.`name`,`location`.`description`,`location`.`address1`,`location`.`address2`,`location`.`city_village`,`location`.`state_province`,`location`.`postal_code`,`location`.`country`,`location`.`latitude`,`location`.`longitude`,`location`.`creator`,`location`.`date_created`,`location`.`county_district`,`location`.`address3`,`location`.`address4`,`location`.`address5`,`location`.`address6`,`location`.`retired`,`location`.`retired_by`,`location`.`date_retired`,`location`.`retire_reason`,`location`.`parent_location`,`location`.`uuid` FROM `location`;
INSERT INTO `location` (`location_id`, `name`, `description`, `address1`, `address2`, `city_village`, `state_province`, `postal_code`, `country`, `latitude`, `longitude`, `creator`, `date_created`, `county_district`, `address3`, `address4`, `address5`, `address6`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `parent_location`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- location_attribute
SELECT `location_attribute`.`location_attribute_id`,`location_attribute`.`location_id`,`location_attribute`.`attribute_type_id`,`location_attribute`.`value_reference`,`location_attribute`.`uuid`,`location_attribute`.`creator`,`location_attribute`.`date_created`,`location_attribute`.`changed_by`,`location_attribute`.`date_changed`,`location_attribute`.`voided`,`location_attribute`.`voided_by`,`location_attribute`.`date_voided`,`location_attribute`.`void_reason` FROM `location_attribute`;
INSERT INTO `location_attribute` (`location_attribute_id`, `location_id`, `attribute_type_id`, `value_reference`, `uuid`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- location_attribute_type
SELECT `location_attribute_type`.`location_attribute_type_id`,`location_attribute_type`.`name`,`location_attribute_type`.`description`,`location_attribute_type`.`datatype`,`location_attribute_type`.`datatype_config`,`location_attribute_type`.`preferred_handler`,`location_attribute_type`.`handler_config`,`location_attribute_type`.`min_occurs`,`location_attribute_type`.`max_occurs`,`location_attribute_type`.`creator`,`location_attribute_type`.`date_created`,`location_attribute_type`.`changed_by`,`location_attribute_type`.`date_changed`,`location_attribute_type`.`retired`,`location_attribute_type`.`retired_by`,`location_attribute_type`.`date_retired`,`location_attribute_type`.`retire_reason`,`location_attribute_type`.`uuid`FROM `location_attribute_type`;
INSERT INTO `location_attribute_type` (`location_attribute_type_id`, `name`, `description`, `datatype`, `datatype_config`, `preferred_handler`, `handler_config`, `min_occurs`, `max_occurs`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- location_tag
SELECT `location_tag`.`location_tag_id`,`location_tag`.`name`,`location_tag`.`description`,`location_tag`.`creator`,`location_tag`.`date_created`,`location_tag`.`retired`,`location_tag`.`retired_by`,`location_tag`.`date_retired`,`location_tag`.`retire_reason`,`location_tag`.`uuid` FROM `location_tag`;
INSERT INTO `location_tag` (`location_tag_id`, `name`, `description`, `creator`, `date_created`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- location_tag_map
SELECT `location_tag_map`.`location_id`,`location_tag_map`.`location_tag_id` FROM `location_tag_map`;
INSERT INTO `location_tag_map` (`location_id`, `location_tag_id`) VALUES (?, ?); 

-- logic_rule_definition
SELECT `logic_rule_definition`.`id`, `logic_rule_definition`.`uuid`, `logic_rule_definition`.`name`,`logic_rule_definition`.`description`,`logic_rule_definition`.`rule_content`,`logic_rule_definition`.`language`,`logic_rule_definition`.`creator`,`logic_rule_definition`.`date_created`,`logic_rule_definition`.`changed_by`,`logic_rule_definition`.`date_changed`,`logic_rule_definition`.`retired`,`logic_rule_definition`.`retired_by`,`logic_rule_definition`.`date_retired`,`logic_rule_definition`.`retire_reason` FROM `logic_rule_definition`;
INSERT INTO `logic_rule_definition` (`id`, `uuid`, `name`, `description`, `rule_content`, `language`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- logic_rule_token
SELECT `logic_rule_token`.`logic_rule_token_id`,`logic_rule_token`.`creator`,`logic_rule_token`.`date_created`,`logic_rule_token`.`changed_by`,`logic_rule_token`.`date_changed`,`logic_rule_token`.`token`,`logic_rule_token`.`class_name`, `logic_rule_token`.`state`,`logic_rule_token`.`uuid` FROM `logic_rule_token`;
INSERT INTO `logic_rule_token` (`logic_rule_token_id`, `creator`, `date_created`, `changed_by`, `date_changed`, `token`, `class_name`, `state`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- logic_rule_token_tag
SELECT `logic_rule_token_tag`.`logic_rule_token_id`,`logic_rule_token_tag`.`tag` FROM `logic_rule_token_tag`;
INSERT INTO `logic_rule_token_tag` (`logic_rule_token_id`, `tag`) VALUES (?, ?); 

-- logic_token_registration
SELECT `logic_token_registration`.`token_registration_id`,`logic_token_registration`.`creator`,`logic_token_registration`.`date_created`,`logic_token_registration`.`changed_by`,`logic_token_registration`.`date_changed`,`logic_token_registration`.`token`,`logic_token_registration`.`provider_class_name`,`logic_token_registration`.`provider_token`,`logic_token_registration`.`configuration`,`logic_token_registration`.`uuid` FROM `logic_token_registration`;
INSERT INTO `logic_token_registration` (`token_registration_id`, `creator`, `date_created`, `changed_by`, `date_changed`, `token`, `provider_class_name`, `provider_token`, `configuration`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- logic_token_registration_tag
SELECT `logic_token_registration_tag`.`token_registration_id`,`logic_token_registration_tag`.`tag` FROM `logic_token_registration_tag`;
INSERT INTO `logic_token_registration_tag` (`token_registration_id`, `tag`) VALUES (?, ?); 

-- note
SELECT `note`.`note_id`,`note`.`note_type`,`note`.`patient_id`,`note`.`obs_id`,`note`.`encounter_id`,`note`.`text`,`note`.`priority`,`note`.`parent`,`note`.`creator`,`note`.`date_created`,`note`.`changed_by`, `note`.`date_changed`, `note`.`uuid` FROM `note`;
INSERT INTO `note` (`note_id`, `note_type`, `patient_id`, `obs_id`, `encounter_id`, `text`, `priority`, `parent`, `creator`, `date_created`, `changed_by`, `date_changed`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- notification_alert
SELECT `notification_alert`.`alert_id`,`notification_alert`.`text`,`notification_alert`.`satisfied_by_any`,`notification_alert`.`alert_read`,`notification_alert`.`date_to_expire``notification_alert`.`creator`,`notification_alert`.`date_created`,`notification_alert`.`changed_by`,`notification_alert`.`date_changed`,`notification_alert`.`uuid` FROM `notification_alert`;
INSERT INTO `notification_alert` (`alert_id`, `text`, `satisfied_by_any`, `alert_read`, `date_to_expire`, `creator`, `date_created`, `changed_by`, `date_changed`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- notification_alert_recipient
SELECT `notification_alert_recipient`.`alert_id`,`notification_alert_recipient`.`user_id`,`notification_alert_recipient`.`alert_read`,`notification_alert_recipient`.`date_changed`,`notification_alert_recipient`.`uuid` FROM `notification_alert_recipient`;
INSERT INTO `notification_alert_recipient` (`alert_id`, `user_id`, `alert_read`, `date_changed`, `uuid`) VALUES (?, ?, ?, ?, ?); 

-- notification_template
SELECT `notification_template`.`template_id`,`notification_template`.`name`,`notification_template`.`template`,`notification_template`.`subject`,`notification_template`.`sender`,`notification_template`.`recipients`,`notification_template`.`ordinal`,`notification_template`.`uuid`FROM `notification_template`;
INSERT INTO `notification_template` (`template_id`, `name`, `template`, `subject`, `sender`, `recipients`, `ordinal`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?); 

-- obs
SELECT `obs`.`obs_id`,`obs`.`person_id`,`obs`.`concept_id`,`obs`.`encounter_id`,`obs`.`order_id`,`obs`.`obs_datetime`,`obs`.`location_id`,`obs`.`obs_group_id`,`obs`.`accession_number`,`obs`.`value_group_id`,`obs`.`value_boolean`,`obs`.`value_coded`,`obs`.`value_coded_name_id`,`obs`.`value_drug`,`obs`.`value_datetime`,`obs`.`value_numeric`,`obs`.`value_modifier`,`obs`.`value_text`,`obs`.`value_complex`,`obs`.`comments`,`obs`.`creator`,`obs`.`date_created`,`obs`.`voided`,`obs`.`voided_by`,`obs`.`date_voided`,`obs`.`void_reason`,`obs`.`uuid`,`obs`.`previous_version`FROM `obs`;
INSERT INTO `obs` (`obs_id`, `person_id`, `concept_id`, `encounter_id`, `order_id`, `obs_datetime`, `location_id`, `obs_group_id`, `accession_number`, `value_group_id`, `value_boolean`, `value_coded`, `value_coded_name_id`, `value_drug`, `value_datetime`, `value_numeric`, `value_modifier`, `value_text`, `value_complex`, `comments`, `creator`, `date_created`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`, `previous_version`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- order_type
SELECT `order_type`.`order_type_id`,`order_type`.`name`,`order_type`.`description`,`order_type`.`creator`,`order_type`.`date_created`,`order_type`.`retired`,`order_type`.`retired_by`,`order_type`.`date_retired`,`order_type`.`retire_reason`,`order_type`.`uuid`FROM `order_type`;
INSERT INTO `order_type` (`order_type_id`, `name`, `description`, `creator`, `date_created`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- orders
SELECT `orders`.`order_id`,`orders`.`order_type_id`,`orders`.`concept_id`,`orders`.`orderer`,`orders`.`encounter_id`,`orders`.`instructions`,`orders`.`start_date`,`orders`.`auto_expire_date`,`orders`.`discontinued`,`orders`.`discontinued_date`,`orders`.`discontinued_by`,`orders`.`discontinued_reason`,`orders`.`discontinued_reason_non_coded`,`orders`.`creator`,`orders`.`date_created`,`orders`.`voided`,`orders`.`voided_by`,`orders`.`date_voided`, `orders`.`void_reason`,`orders`.`patient_id`,`orders`.`accession_number`,`orders`.`uuid`,`orders`.`urgency` FROM `orders`;
INSERT INTO `minetb_dw`.`orders` (`order_id`, `order_type_id`, `concept_id`, `orderer`, `encounter_id`, `instructions`, `start_date`, `auto_expire_date`, `discontinued`, `discontinued_date`, `discontinued_by`, `discontinued_reason`, `discontinued_reason_non_coded`, `creator`, `date_created`, `voided`, `voided_by`, `date_voided`, `void_reason`, `patient_id`, `accession_number`, `uuid`, `urgency`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- patient
SELECT `patient`.`patient_id`,`patient`.`tribe`,`patient`.`creator`,`patient`.`date_created`,`patient`.`changed_by`,`patient`.`date_changed`,`patient`.`voided`,`patient`.`voided_by`,`patient`.`date_voided`,`patient`.`void_reason` FROM `patient`;
INSERT INTO `patient` (`patient_id`, `tribe`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- patient_identifier
SELECT `patient_identifier`.`patient_identifier_id`,`patient_identifier`.`patient_id`,`patient_identifier`.`identifier`,`patient_identifier`.`identifier_type`,`patient_identifier`.`preferred`,`patient_identifier`.`location_id`,`patient_identifier`.`creator`,`patient_identifier`.`date_created`,`patient_identifier`.`date_changed`,`patient_identifier`.`changed_by`,`patient_identifier`.`voided`,`patient_identifier`.`voided_by`,`patient_identifier`.`date_voided`,`patient_identifier`.`void_reason`,`patient_identifier`.`uuid` FROM `patient_identifier`;
INSERT INTO `patient_identifier` (`patient_identifier_id`, `patient_id`, `identifier`, `identifier_type`, `preferred`, `location_id`, `creator`, `date_created`, `date_changed`, `changed_by`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- patient_identifier_type
SELECT `patient_identifier_type`.`patient_identifier_type_id`,`patient_identifier_type`.`name`,`patient_identifier_type`.`description`,`patient_identifier_type`.`format`,`patient_identifier_type`.`check_digit`,`patient_identifier_type`.`creator`,`patient_identifier_type`.`date_created`,`patient_identifier_type`.`required`,`patient_identifier_type`.`format_description`,`patient_identifier_type`.`validator`,`patient_identifier_type`.`location_behavior`,`patient_identifier_type`.`retired`,`patient_identifier_type`.`retired_by`,`patient_identifier_type`.`date_retired`,`patient_identifier_type`.`retire_reason`,`patient_identifier_type`.`uuid` FROM `patient_identifier_type`;
INSERT INTO `patient_identifier_type` (`patient_identifier_type_id`, `name`, `description`, `format`, `check_digit`, `creator`, `date_created`, `required`, `format_description`, `validator`, `location_behavior`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- patient_program
SELECT `patient_program`.`patient_program_id`,`patient_program`.`patient_id`,`patient_program`.`program_id`,`patient_program`.`date_enrolled`,`patient_program`.`date_completed`,`patient_program`.`location_id`,`patient_program`.`outcome_concept_id`,`patient_program`.`creator`,`patient_program`.`date_created`,`patient_program`.`changed_by`,`patient_program`.`date_changed`,`patient_program`.`voided`,`patient_program`.`voided_by`,`patient_program`.`date_voided`,`patient_program`.`void_reason`,`patient_program`.`uuid` FROM `patient_program`;
INSERT INTO `patient_program` (`patient_program_id`, `patient_id`, `program_id`, `date_enrolled`, `date_completed`, `location_id`, `outcome_concept_id`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- patient_state
SELECT `patient_state`.`patient_state_id`,`patient_state`.`patient_program_id`,`patient_state`.`state`,`patient_state`.`start_date`,`patient_state`.`end_date`,`patient_state`.`creator`,`patient_state`.`date_created`,`patient_state`.`changed_by`, `patient_state`.`date_changed`,`patient_state`.`voided`,`patient_state`.`voided_by`,`patient_state`.`date_voided`,`patient_state`.`void_reason`,`patient_state`.`uuid` FROM `patient_state`;
INSERT INTO `patient_state` (`patient_state_id`, `patient_program_id`, `state`, `start_date`, `end_date`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- person
SELECT `person`.`person_id`,`person`.`gender`,`person`.`birthdate`,`person`.`birthdate_estimated`,`person`.`dead`,`person`.`death_date`,`person`.`cause_of_death`,`person`.`creator`,`person`.`date_created`,`person`.`changed_by`,`person`.`date_changed`,`person`.`voided`,`person`.`voided_by`,`person`.`date_voided`,`person`.`void_reason`,`person`.`uuid` FROM `person`;
INSERT INTO `person` (`person_id`, `gender`, `birthdate`, `birthdate_estimated`, `dead`, `death_date`, `cause_of_death`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- person_address
SELECT `person_address`.`person_address_id`,`person_address`.`person_id`,`person_address`.`preferred`,`person_address`.`address1`,`person_address`.`address2`,`person_address`.`city_village`,`person_address`.`state_province`,`person_address`.`postal_code`,`person_address`.`country`,`person_address`.`latitude`,`person_address`.`longitude`,`person_address`.`start_date`,`person_address`.`end_date`,`person_address`.`creator`,`person_address`.`date_created`,`person_address`.`voided`,`person_address`.`voided_by`,`person_address`.`date_voided`,`person_address`.`void_reason`,`person_address`.`county_district`,`person_address`.`address3`,`person_address`.`address4`,`person_address`.`address5`,`person_address`.`address6`,`person_address`.`date_changed`,`person_address`.`changed_by`,`person_address`.`uuid` FROM `person_address`;
INSERT INTO `person_address` (`person_address_id`, `person_id`, `preferred`, `address1`, `address2`, `city_village`, `state_province`, `postal_code`, `country`, `latitude`, `longitude`, `start_date`, `end_date`, `creator`, `date_created`, `voided`, `voided_by`, `date_voided`, `void_reason`, `county_district`, `address3`, `address4`, `address5`, `address6`, `date_changed`, `changed_by`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- person_attribute
SELECT `person_attribute`.`person_attribute_id`,`person_attribute`.`person_id`,`person_attribute`.`value`,`person_attribute`.`person_attribute_type_id`,`person_attribute`.`creator`,`person_attribute`.`date_created`,`person_attribute`.`changed_by`,`person_attribute`.`date_changed`, `person_attribute`.`voided`,`person_attribute`.`voided_by`,`person_attribute`.`date_voided`,`person_attribute`.`void_reason`,`person_attribute`.`uuid` FROM `person_attribute`;
INSERT INTO `person_attribute` (`person_attribute_id`, `person_id`, `value`, `person_attribute_type_id`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- person_attribute_type
SELECT `person_attribute_type`.`person_attribute_type_id`,`person_attribute_type`.`name`,`person_attribute_type`.`description`,`person_attribute_type`.`format`,`person_attribute_type`.`foreign_key`,`person_attribute_type`.`searchable`,`person_attribute_type`.`creator`,`person_attribute_type`.`date_created`,`person_attribute_type`.`changed_by`,`person_attribute_type`.`date_changed`,`person_attribute_type`.`retired`,`person_attribute_type`.`retired_by`,`person_attribute_type`.`date_retired`,`person_attribute_type`.`retire_reason`,`person_attribute_type`.`edit_privilege`,`person_attribute_type`.`sort_weight`,`person_attribute_type`.`uuid` FROM `person_attribute_type`;
INSERT INTO `person_attribute_type` (`person_attribute_type_id`, `name`, `description`, `format`, `foreign_key`, `searchable`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `edit_privilege`, `sort_weight`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>, ?); 

-- person_merge_log
SELECT `person_merge_log`.`person_merge_log_id`,`person_merge_log`.`winner_person_id`, `person_merge_log`.`loser_person_id`,`person_merge_log`.`creator`,`person_merge_log`.`date_created`,`person_merge_log`.`merged_data`,`person_merge_log`.`uuid`,`person_merge_log`.`changed_by`, `person_merge_log`.`date_changed`,`person_merge_log`.`voided`,`person_merge_log`.`voided_by`,`person_merge_log`.`date_voided`,`person_merge_log`.`void_reason` FROM `person_merge_log`;
INSERT INTO `person_merge_log` (`person_merge_log_id`, `winner_person_id`, `loser_person_id`, `creator`, `date_created`, `merged_data`, `uuid`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- person_name
SELECT `person_name`.`person_name_id`,`person_name`.`preferred`,`person_name`.`person_id`,`person_name`.`prefix`,`person_name`.`given_name`,`person_name`.`middle_name`,`person_name`.`family_name_prefix`, `person_name`.`family_name`,`person_name`.`family_name2`, `person_name`.`family_name_suffix`,`person_name`.`degree`,`person_name`.`creator`,`person_name`.`date_created`,`person_name`.`voided`,`person_name`.`voided_by`,`person_name`.`date_voided`,`person_name`.`void_reason`,`person_name`.`changed_by`,`person_name`.`date_changed`,`person_name`.`uuid` FROM `person_name`;
INSERT INTO `person_name` (`person_name_id`, `preferred`, `person_id`, `prefix`, `given_name`, `middle_name`, `family_name_prefix`, `family_name`, `family_name2`, `family_name_suffix`, `degree`, `creator`, `date_created`, `voided`, `voided_by`, `date_voided`, `void_reason`, `changed_by`, `date_changed`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- privilege
SELECT `privilege`.`privilege`,`privilege`.`description`,`privilege`.`uuid` FROM `privilege`;
INSERT INTO `privilege` (`privilege`, `description`, `uuid`) VALUES (?, ?, ?); 

-- program
SELECT `program`.`program_id`,`program`.`concept_id`,`program`.`outcomes_concept_id`,`program`.`creator`,`program`.`date_created`,`program`.`changed_by`,`program`.`date_changed`,`program`.`retired`,`program`.`name`,`program`.`description`,`program`.`uuid` FROM `program`;
INSERT INTO `program` (`program_id`, `concept_id`, `outcomes_concept_id`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `name`, `description`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- program_workflow
SELECT `program_workflow`.`program_workflow_id`,`program_workflow`.`program_id`,`program_workflow`.`concept_id`,`program_workflow`.`creator`,`program_workflow`.`date_created`,`program_workflow`.`retired`,`program_workflow`.`changed_by`,`program_workflow`.`date_changed`,`program_workflow`.`uuid` FROM `program_workflow`;
INSERT INTO `program_workflow` (`program_workflow_id`, `program_id`, `concept_id`, `creator`, `date_created`, `retired`, `changed_by`, `date_changed`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- program_work_flow_state
SELECT `program_workflow_state`.`program_workflow_state_id`,`program_workflow_state`.`program_workflow_id`,`program_workflow_state`.`concept_id`,`program_workflow_state`.`initial`,`program_workflow_state`.`terminal`,`program_workflow_state`.`creator`,`program_workflow_state`.`date_created`,`program_workflow_state`.`retired`,`program_workflow_state`.`changed_by`,`program_workflow_state`.`date_changed`,`program_workflow_state`.`uuid` FROM `program_workflow_state`;
INSERT INTO `program_workflow_state` (`program_workflow_state_id`, `program_workflow_id`, `concept_id`, `initial`, `terminal`, `creator`, `date_created`, `retired`, `changed_by`, `date_changed`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- provider
SELECT `provider`.`provider_id`,`provider`.`person_id`,`provider`.`name`,`provider`.`identifier`,`provider`.`creator`,`provider`.`date_created`,`provider`.`changed_by`,`provider`.`date_changed`,`provider`.`retired`,`provider`.`retired_by`,`provider`.`date_retired`,`provider`.`retire_reason`,`provider`.`uuid` FROM `provider`;
INSERT INTO `provider` (`provider_id`, `person_id`, `name`, `identifier`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- provider_attribute
SELECT `provider_attribute`.`provider_attribute_id`,`provider_attribute`.`provider_id`,`provider_attribute`.`attribute_type_id`,`provider_attribute`.`value_reference`,`provider_attribute`.`uuid`,`provider_attribute`.`creator`,`provider_attribute`.`date_created`,`provider_attribute`.`changed_by`,`provider_attribute`.`date_changed`,`provider_attribute`.`voided`,`provider_attribute`.`voided_by`,`provider_attribute`.`date_voided`,`provider_attribute`.`void_reason` FROM `provider_attribute`;
INSERT INTO `provider_attribute` (`provider_attribute_id`, `provider_id`, `attribute_type_id`, `value_reference`, `uuid`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- provider_attribute_type
SELECT `provider_attribute_type`.`provider_attribute_type_id`,`provider_attribute_type`.`name`,`provider_attribute_type`.`description`,`provider_attribute_type`.`datatype`,`provider_attribute_type`.`datatype_config`,`provider_attribute_type`.`preferred_handler`,`provider_attribute_type`.`handler_config`,`provider_attribute_type`.`min_occurs`,`provider_attribute_type`.`max_occurs`,`provider_attribute_type`.`creator`,`provider_attribute_type`.`date_created`,`provider_attribute_type`.`changed_by`,`provider_attribute_type`.`date_changed`,`provider_attribute_type`.`retired`,`provider_attribute_type`.`retired_by`,`provider_attribute_type`.`date_retired`,`provider_attribute_type`.`retire_reason`,`provider_attribute_type`.`uuid` FROM `provider_attribute_type`;
INSERT INTO `provider_attribute_type` (`provider_attribute_type_id`, `name`, `description`, `datatype`, `datatype_config`, `preferred_handler`, `handler_config`, `min_occurs`, `max_occurs`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- relationship
SELECT `relationship`.`relationship_id`,`relationship`.`person_a`,`relationship`.`relationship`,`relationship`.`person_b`,`relationship`.`start_date`,`relationship`.`end_date`,`relationship`.`creator`,`relationship`.`date_created`,`relationship`.`date_changed`,`relationship`.`changed_by`,`relationship`.`voided`,`relationship`.`voided_by`,`relationship`.`date_voided`,`relationship`.`void_reason`,`relationship`.`uuid` FROM `relationship`;
INSERT INTO `minetb`.`relationship` (`relationship_id`, `person_a`, `relationship`, `person_b`, `start_date`, `end_date`, `creator`, `date_created`, `date_changed`, `changed_by`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- relationship_type
SELECT `relationship_type`.`relationship_type_id`,`relationship_type`.`a_is_to_b`,`relationship_type`.`b_is_to_a`,`relationship_type`.`preferred`,`relationship_type`.`weight`,`relationship_type`.`description`,`relationship_type`.`creator`,`relationship_type`.`date_created`,`relationship_type`.`retired`,`relationship_type`.`retired_by`,`relationship_type`.`date_retired`,`relationship_type`.`retire_reason`, `relationship_type`.`uuid` FROM `relationship_type`;
INSERT INTO `relationship_type` (`relationship_type_id`, `a_is_to_b`, `b_is_to_a`, `preferred`, `weight`, `description`, `creator`, `date_created`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- report_object
SELECT `report_object`.`report_object_id`,`report_object`.`name`,`report_object`.`description`,`report_object`.`report_object_type`,`report_object`.`report_object_sub_type`,`report_object`.`xml_data`,`report_object`.`creator`,`report_object`.`date_created`,`report_object`.`changed_by`,`report_object`.`date_changed`,`report_object`.`voided`,`report_object`.`voided_by`,`report_object`.`date_voided`,`report_object`.`void_reason`,`report_object`.`uuid` FROM `report_object`;
INSERT INTO `report_object` (`report_object_id`, `name`, `description`, `report_object_type`, `report_object_sub_type`, `xml_data`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?; 

-- report_schema_xml
SELECT `report_schema_xml`.`report_schema_id`,`report_schema_xml`.`name`,`report_schema_xml`.`description`,`report_schema_xml`.`xml_data`,`report_schema_xml`.`uuid` FROM `report_schema_xml`;
INSERT INTO `report_schema_xml` (`report_schema_id`, `name`, `description`, `xml_data`, `uuid`) VALUES (?, ?, ?, ?, ?); 

-- reporting_report_design
SELECT `reporting_report_design`.`id`,`reporting_report_design`.`uuid`,`reporting_report_design`.`name`,`reporting_report_design`.`description`,`reporting_report_design`.`renderer_type`,`reporting_report_design`.`properties`,`reporting_report_design`.`creator`,`reporting_report_design`.`date_created`,`reporting_report_design`.`changed_by`,`reporting_report_design`.`date_changed`,`reporting_report_design`.`retired`,`reporting_report_design`.`retired_by`,`reporting_report_design`.`date_retired`,`reporting_report_design`.`retire_reason`,`reporting_report_design`.`report_definition_uuid` FROM `reporting_report_design`;
INSERT INTO `reporting_report_design` (`id`, `uuid`, `name`, `description`, `renderer_type`, `properties`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `report_definition_uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- reporting_report_design_resource
SELECT `reporting_report_design_resource`.`id`,`reporting_report_design_resource`.`uuid`,`reporting_report_design_resource`.`name`,`reporting_report_design_resource`.`description`,`reporting_report_design_resource`.`report_design_id`,`reporting_report_design_resource`.`content_type`,`reporting_report_design_resource`.`extension`, `reporting_report_design_resource`.`contents`, `reporting_report_design_resource`.`creator`,`reporting_report_design_resource`.`date_created`,`reporting_report_design_resource`.`changed_by`,`reporting_report_design_resource`.`date_changed`,`reporting_report_design_resource`.`retired`,`reporting_report_design_resource`.`retired_by`,`reporting_report_design_resource`.`date_retired`,`reporting_report_design_resource`.`retire_reason` FROM `reporting_report_design_resource`;
INSERT INTO `reporting_report_design_resource` (`id`, `uuid`, `name`, `description`, `report_design_id`, `content_type`, `extension`, `contents`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- reporting_report_processor
SELECT `reporting_report_processor`.`id`,`reporting_report_processor`.`uuid`,`reporting_report_processor`.`name`,`reporting_report_processor`.`description`,`reporting_report_processor`.`processor_type`,`reporting_report_processor`.`configuration`,`reporting_report_processor`.`run_on_success`,`reporting_report_processor`.`run_on_error`,`reporting_report_processor`.`creator`,`reporting_report_processor`.`date_created`,`reporting_report_processor`.`changed_by`,`reporting_report_processor`.`date_changed`,`reporting_report_processor`.`retired`,`reporting_report_processor`.`retired_by`,`reporting_report_processor`.`date_retired`,`reporting_report_processor`.`retire_reason`, `reporting_report_processor`.`report_design_id`,`reporting_report_processor`.`processor_mode` FROM `reporting_report_processor`;
INSERT INTO `reporting_report_processor` (`id`, `uuid`, `name`, `description`, `processor_type`, `configuration`, `run_on_success`, `run_on_error`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `report_design_id`, `processor_mode`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- reporting_report_request
SELECT `reporting_report_request`.`id`,`reporting_report_request`.`uuid`, `reporting_report_request`.`base_cohort_uuid`,`reporting_report_request`.`base_cohort_parameters`,`reporting_report_request`.`report_definition_uuid`,`reporting_report_request`.`report_definition_parameters`,`reporting_report_request`.`renderer_type`,`reporting_report_request`.`renderer_argument`,`reporting_report_request`.`requested_by`,`reporting_report_request`.`request_datetime`,`reporting_report_request`.`priority`,`reporting_report_request`.`status`,`reporting_report_request`.`evaluation_start_datetime`,`reporting_report_request`.`evaluation_complete_datetime`,`reporting_report_request`.`render_complete_datetime`,`reporting_report_request`.`description`,`reporting_report_request`.`schedule`,`reporting_report_request`.`process_automatically` FROM `reporting_report_request`;
INSERT INTO `reporting_report_request` (`id`, `uuid`, `base_cohort_uuid`, `base_cohort_parameters`, `report_definition_uuid`, `report_definition_parameters`, `renderer_type`, `renderer_argument`, `requested_by`, `request_datetime`, `priority`, `status`, `evaluation_start_datetime`, `evaluation_complete_datetime`, `render_complete_datetime`, `description`, `schedule`, `process_automatically`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- role
SELECT `role`.`role`,`role`.`description`,`role`.`uuid` FROM `role`;
INSERT INTO `role` (`role`, `description`, `uuid`) VALUES (?,?,?);

-- role_privilege
SELECT `role_privilege`.`role`,`role_privilege`.`privilege`FROM `role_privilege`;
INSERT INTO `role_privilege`(`role`,`privilege`) VALUES(?,?);

-- role_role
SELECT `role_role`.`parent_role`, `role_role`.`child_role` FROM `role_role`;
INSERT INTO `role_role` (`parent_role`, `child_role`) VALUES (?, ?); 

-- scheduler_task_config
SELECT `scheduler_task_config`.`task_config_id`,`scheduler_task_config`.`name`,`scheduler_task_config`.`description`,`scheduler_task_config`.`schedulable_class`,`scheduler_task_config`.`start_time`,`scheduler_task_config`.`start_time_pattern`,`scheduler_task_config`.`repeat_interval`,`scheduler_task_config`.`start_on_startup`,`scheduler_task_config`.`started`,`scheduler_task_config`.`created_by`,`scheduler_task_config`.`date_created`,`scheduler_task_config`.`changed_by`,`scheduler_task_config`.`date_changed`,`scheduler_task_config`.`last_execution_time`,`scheduler_task_config`.`uuid` FROM `scheduler_task_config`;
INSERT INTO `scheduler_task_config` (`task_config_id`, `name`, `description`, `schedulable_class`, `start_time`, `start_time_pattern`, `repeat_interval`, `start_on_startup`, `started`, `created_by`, `date_created`, `changed_by`, `date_changed`, `last_execution_time`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- scheduler_task_config_property
SELECT `scheduler_task_config_property`.`task_config_property_id`,`scheduler_task_config_property`.`name`,`scheduler_task_config_property`.`value`,`scheduler_task_config_property`.`task_config_id` FROM `scheduler_task_config_property`;
INSERT INTO `scheduler_task_config_property` (`task_config_property_id`, `name`, `value`, `task_config_id`) VALUES (?, ?, ?, ?); 

-- test_order
SELECT `test_order`.`order_id`,`test_order`.`specimen_source`,`test_order`.`laterality`,`test_order`.`clinical_history` FROM `test_order`;
INSERT INTO `test_order` (`order_id`, `specimen_source`, `laterality`, `clinical_history`) VALUES (?, ?, ?, ?); 

-- user_property
SELECT `user_property`.`user_id`,`user_property`.`property`,`user_property`.`property_value` FROM `user_property`;
INSERT INTO `user_property` (`user_id`, `property`, `property_value`) VALUES (?, ?, ?); 

-- user_role
SELECT `user_role`.`user_id`,`user_role`.`role` FROM `user_role`;
INSERT INTO `user_role` (`user_id`, `role`) VALUES (?, ?); 

-- users
SELECT `users`.`user_id`,`users`.`system_id`,`users`.`username`,`users`.`password`,`users`.`salt`,`users`.`secret_question`,`users`.`secret_answer`,`users`.`creator`,`users`.`date_created`,`users`.`changed_by`,`users`.`date_changed`,`users`.`person_id`,`users`.`retired`,`users`.`retired_by`,`users`.`date_retired`,`users`.`retire_reason`,`users`.`uuid` FROM `users`;
INSERT INTO `users` (`user_id`, `system_id`, `username`, `password`, `salt`, `secret_question`, `secret_answer`, `creator`, `date_created`, `changed_by`, `date_changed`, `person_id`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- visit
SELECT `visit`.`visit_id`,`visit`.`patient_id`,`visit`.`visit_type_id`,`visit`.`date_started`,`visit`.`date_stopped`,`visit`.`indication_concept_id`,`visit`.`location_id`,`visit`.`creator`,`visit`.`date_created`,`visit`.`changed_by`,`visit`.`date_changed`,`visit`.`voided`,`visit`.`voided_by`,`visit`.`date_voided`,`visit`.`void_reason`,`visit`.`uuid` FROM `visit`;
INSERT INTO `visit` (`visit_id`, `patient_id`, `visit_type_id`, `date_started`, `date_stopped`, `indication_concept_id`, `location_id`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- visit_attribute
SELECT `visit_attribute`.`visit_attribute_id`,`visit_attribute`.`visit_id`,`visit_attribute`.`attribute_type_id`,`visit_attribute`.`value_reference`, `visit_attribute`.`uuid`,`visit_attribute`.`creator`,`visit_attribute`.`date_created`,`visit_attribute`.`changed_by`,`visit_attribute`.`date_changed`,`visit_attribute`.`voided`,`visit_attribute`.`voided_by`,`visit_attribute`.`date_voided`,`visit_attribute`.`void_reason` FROM `visit_attribute`;
INSERT INTO `visit_attribute` (`visit_attribute_id`, `visit_id`, `attribute_type_id`, `value_reference`, `uuid`, `creator`, `date_created`, `changed_by`, `date_changed`, `voided`, `voided_by`, `date_voided`, `void_reason`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- visit_attribute_type
SELECT `visit_attribute_type`.`visit_attribute_type_id`,`visit_attribute_type`.`name`,`visit_attribute_type`.`description`,`visit_attribute_type`.`datatype`,`visit_attribute_type`.`datatype_config`,`visit_attribute_type`.`preferred_handler`,`visit_attribute_type`.`handler_config`,`visit_attribute_type`.`min_occurs`,`visit_attribute_type`.`max_occurs`,`visit_attribute_type`.`creator`,`visit_attribute_type`.`date_created`,`visit_attribute_type`.`changed_by`,`visit_attribute_type`.`date_changed`,`visit_attribute_type`.`retired`,`visit_attribute_type`.`retired_by`,`visit_attribute_type`.`date_retired`,`visit_attribute_type`.`retire_reason`,`visit_attribute_type`.`uuid` FROM `visit_attribute_type`;
INSERT INTO `visit_attribute_type` (`visit_attribute_type_id`, `name`, `description`, `datatype`, `datatype_config`, `preferred_handler`, `handler_config`, `min_occurs`, `max_occurs`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 

-- visit_type
SELECT `visit_type`.`visit_type_id`,`visit_type`.`name`,`visit_type`.`description`,`visit_type`.`creator`,`visit_type`.`date_created`,`visit_type`.`changed_by`,`visit_type`.`date_changed`,`visit_type`.`retired`,`visit_type`.`retired_by`,`visit_type`.`date_retired`,`visit_type`.`retire_reason`,`visit_type`.`uuid` FROM `visit_type`;
INSERT INTO `visit_type` (`visit_type_id`, `name`, `description`, `creator`, `date_created`, `changed_by`, `date_changed`, `retired`, `retired_by`, `date_retired`, `retire_reason`, `uuid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 