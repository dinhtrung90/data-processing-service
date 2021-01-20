DROP TABLE IF EXISTS `vts_eligibility_process_error`;
CREATE TABLE `vts_eligibility_process_error` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `validation_error` text,
                                   `source_id` varchar(255) NOT NULL,
                                   `created_by` varchar(100) NULL,
                                   `created_date` datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
                                   `last_modified_by` varchar(100) NULL,
                                   `last_modified_date` datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`)

);

create index vts_eligibility_process_error_source_id
    on vts_eligibility_process_error (source_id);
