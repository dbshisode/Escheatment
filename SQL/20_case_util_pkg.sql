create or replace package v3owner01.case_util_pkg
as	
	-------------------------------------------------------------------------------------
	TYPE type_document IS TABLE OF v3owner01.document%ROWTYPE INDEX BY PLS_INTEGER;
	TYPE type_case_history IS TABLE OF v3owner01.case_history%ROWTYPE INDEX BY PLS_INTEGER;
	TYPE type_case_hist_doc_assoc IS TABLE OF v3owner01.case_hist_doc_assoc%ROWTYPE INDEX BY PLS_INTEGER;
	TYPE type_note IS TABLE OF v3owner01.note%ROWTYPE INDEX BY PLS_INTEGER;

	-------------------------------------------------------------------------------------
	procedure p_add_case_hist_entry (
		a_result			   in out number,
		a_case_id              in number,
        a_user_name            in varchar,
        a_doc_name             in varchar,
		a_doc_id   in number
	);
	-------------------------------------------------------------------------------------
	function f_get_next_line_seq(a_case_id number) 
		return pls_integer; 
	-------------------------------------------------------------------------------------
    procedure p_add_document (
		 a_document_id              in out number
		,a_physical_location        in varchar2
		,a_form_id                  in number
		,a_print_dt                 in timestamp
		,a_create_dt                in timestamp
		,a_create_user_id           in varchar2
		,a_update_dt                in timestamp
		,a_update_user_id           in varchar2
		,a_version                  in number
		,a_external_source_id       in number
		,a_external_source_cd       in varchar2
		,a_doc_id                   in varchar2
		,a_doc_key                  in varchar2
		,a_page_cnt                 in number
		,a_name                     in varchar2
		,a_comments                 in varchar2
		,a_security_level           in number
		,a_mailing_dt               in timestamp
		,a_print_method_cd          in varchar2
		,a_court_cd                 in varchar2
		,a_status_id                in number
		,a_mac_addr                 in varchar2
		,a_case_id                  in number
		,a_batch_number             in varchar2
		,a_efiling_status_id        in number
		,a_document_type_cd         in varchar2
		,a_external_efiling_doc_key in varchar2
		,a_dms_id                   in number         
		,a_preview_ind              in varchar2
		,a_efiling_viewed_ind       in varchar2
		,a_generated_mode           in varchar2
		,a_efiling_acceptance_ind   in varchar2
	);
	-------------------------------------------------------------------------------------
    procedure p_add_case_hist (
		a_case_hist_id          in out number
		,a_changed_ind          in varchar2
		,a_short_text           in varchar2
		,a_long_entry           in varchar2
		,a_sealed_ind           in varchar2
		,a_case_hist_entry_id   in number
		,a_external_source_id   in number
		,a_case_id              in number
		,a_external_source_cd   in varchar2
		,a_display_override_ind in varchar2
		,a_parent_entry_id      in number
		,a_external_dt          in timestamp
		,a_line_num             in number
		,a_sequence_num         in number
		,a_strike_ind           in varchar2
		,a_error_ind            in varchar2
		,a_create_dt            in timestamp
		,a_create_user_id       in varchar2
		,a_update_dt            in timestamp
		,a_update_user_id       in varchar2
		,a_version              in number
		,a_security_level       in number
		,a_non_lead_case_id     in number
		,a_display_case_num     in varchar2
		,a_mac_addr             in varchar2 default null
    );
	
	procedure p_add_hist_doc_assoc (
		a_case_hist_id    in number
		,a_document_id    in number
		,a_version        in number
		,a_create_dt      in timestamp
		,a_create_user_id in varchar2
		,a_update_dt      in timestamp
		,a_update_user_id in varchar2
		,a_mac_addr       in varchar2
	);
	
	procedure p_add_note (
		a_note_id             in out number
		,a_external_source_id in number
		,a_external_source_cd in varchar2
		,a_note_type_ind      in varchar2
		,a_sequence_num       in number
		,a_text               in varchar2
		,a_case_year          in number
		,a_case_id            in number
		,a_version            in number
		,a_create_dt          in timestamp
		,a_create_user_id     in varchar2
		,a_update_dt          in timestamp
		,a_update_user_id     in varchar2
		,a_court_cd           in varchar2
		,a_mac_addr           in varchar2
	);
	
	/* TODO: no need for now, assume that there is always an entry once this service is called
	procedure p_add_case_hist_line_seq(
		a_case_id         in number
		,a_line_num       in number
		,a_version        in number
		,a_create_dt      in timestamp
		,a_create_user_id in varchar2
		,a_update_dt      in timestamp
		,a_update_user_id in varchar2
		,a_mac_addr       in varchar2
		,a_source         in varchar2
	);*/
end case_util_pkg;
/