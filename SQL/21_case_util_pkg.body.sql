create or replace package body v3owner01.case_util_pkg
as
	-------------------------------------------------------------------------------------
	procedure p_add_case_hist_entry (
		a_result			   in out number,
		a_case_id              in number,
        a_user_name            in varchar,
        a_doc_name             in varchar,
		a_doc_id               in number        
	)is
		v_test					pls_integer;
		v_document				type_document;
		v_case_history			type_case_history;
		v_case_hist_doc_assoc	type_case_hist_doc_assoc;
		v_note					type_note;
        v_dms_id                number;
	begin            
        select rtv.code_id 
        into v_dms_id
        from v3owner01.ref_table_value rtv, 
             v3owner01.case c 
        where rtv.table_name = 'DMS_CONFIG' and 
              rtv.related_code_id = c.case_cat_id and 
              c.case_id = a_case_id and 
              rownum = 1;
              
		-- TODO: stub data
		v_document(1).document_id := v3owner01.document_seq.nextval;
		v_document(1).physical_location := null;
		v_document(1).form_id := null;
		v_document(1).print_dt := null;
		v_document(1).create_dt := sysdate;
		v_document(1).create_user_id := a_user_name;
		v_document(1).update_dt := sysdate;
		v_document(1).update_user_id := a_user_name;
		v_document(1).version := 1;
		v_document(1).external_source_id := a_case_id;
		v_document(1).external_source_cd := 'CASE';
		v_document(1).doc_id := a_doc_id;
		v_document(1).doc_key := null;
		v_document(1).page_cnt := null;
		v_document(1).name := a_doc_name;
		v_document(1).comments := null;
		v_document(1).security_level := 1;
		v_document(1).mailing_dt := null;
		v_document(1).print_method_cd := 'NPR';
		v_document(1).court_cd := 30;
		v_document(1).status_id := 8000231;
		v_document(1).mac_addr := null;
		v_document(1).case_id := a_case_id;
		v_document(1).batch_number := null;
		v_document(1).efiling_status_id := null;
		v_document(1).document_type_cd := null;
		v_document(1).external_efiling_doc_key := null;
		v_document(1).dms_id := v_dms_id;
		v_document(1).preview_ind := 'N';
		v_document(1).efiling_viewed_ind := 'N';
		v_document(1).generated_mode := null;
		v_document(1).efiling_acceptance_ind := 'N';
		insert into v3owner01.document values v_document(1);
        a_result := v_document(1).document_id;
		
		v_case_history(1).case_hist_id := v3owner01.case_history_seq.nextval;
		v_case_history(1).changed_ind := 'N';
		v_case_history(1).short_text := a_doc_name;
		v_case_history(1).long_entry := a_doc_name;
		v_case_history(1).sealed_ind := 'N';
		v_case_history(1).case_hist_entry_id := 200000;  --form generation
		v_case_history(1).external_source_id := a_case_id;  --case_id
		v_case_history(1).case_id := a_case_id;
		v_case_history(1).external_source_cd := 'CASE';
		v_case_history(1).display_override_ind := 'Y';
		v_case_history(1).parent_entry_id := null;
		v_case_history(1).external_dt := sysdate;
		v_case_history(1).line_num := f_get_next_line_seq(a_case_id);
		v_case_history(1).sequence_num := null;
		v_case_history(1).strike_ind := 'N';
		v_case_history(1).error_ind := 'N';
		v_case_history(1).create_dt := sysdate;
		v_case_history(1).create_user_id := a_user_name;
		v_case_history(1).update_dt := sysdate;
		v_case_history(1).update_user_id := a_user_name;
		v_case_history(1).version := 1;
		v_case_history(1).security_level := 1;
		v_case_history(1).non_lead_case_id := null;
		v_case_history(1).display_case_num := null;
		v_case_history(1).mac_addr := null;
		insert into v3owner01.case_history values v_case_history(1);
		
		v_case_hist_doc_assoc(1).case_hist_id := v_case_history(1).case_hist_id;
		v_case_hist_doc_assoc(1).document_id := v_document(1).document_id;
		v_case_hist_doc_assoc(1).version := 1;
		v_case_hist_doc_assoc(1).create_dt := sysdate;
		v_case_hist_doc_assoc(1).create_user_id := a_user_name;
		v_case_hist_doc_assoc(1).update_dt := sysdate;
		v_case_hist_doc_assoc(1).update_user_id := a_user_name;
		v_case_hist_doc_assoc(1).mac_addr := null;
		insert into v3owner01.case_hist_doc_assoc values v_case_hist_doc_assoc(1);
		
		-- TODO: Spring should handle this
		commit;
		
	end;
	-------------------------------------------------------------------------------------
	function f_get_next_line_seq(a_case_id number) 
		return pls_integer
	is
		v_line_num 	pls_integer;
		v_exist 	pls_integer;
	begin
		
		select nvl(max(ch.line_num),0) + 1 
		into v_line_num
		from v3owner01.case_history ch 
		where ch.case_id = a_case_id;
		
		/*TODO: no need for now, assume that there is always an entry once this service is called
		select count(*)
		into v_exist
		from v3owner01.case_hist_line_sequence
		where case_id = a_case_id;
		
		if v_exist < 1 then
			Add ???
		end if
		*/
		return v_line_num;
	end;
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
		,a_efiling_acceptance_ind   in varchar2)
	is
	
	begin
		if a_document_id is null or a_document_id < 1 then
			a_document_id := v3owner01.document_seq.nextval;
		end if;
		
		insert into v3owner01.document(
			document_id
			,physical_location
			,form_id
			,print_dt
			,create_dt
			,create_user_id
			,update_dt
			,update_user_id
			,version
			,external_source_id
			,external_source_cd
			,doc_id
			,doc_key
			,page_cnt
			,name
			,comments
			,security_level
			,mailing_dt
			,print_method_cd
			,court_cd
			,status_id
			,mac_addr
			,case_id
			,batch_number
			,efiling_status_id
			,document_type_cd
			,external_efiling_doc_key
			,dms_id
			,preview_ind
			,efiling_viewed_ind
			,generated_mode
			,efiling_acceptance_ind)
		values(
			a_document_id
			,a_physical_location
			,a_form_id
			,a_print_dt
			,a_create_dt
			,a_create_user_id
			,a_update_dt
			,a_update_user_id
			,a_version
			,a_external_source_id
			,a_external_source_cd
			,a_doc_id
			,a_doc_key
			,a_page_cnt
			,a_name
			,a_comments
			,a_security_level
			,a_mailing_dt
			,a_print_method_cd
			,a_court_cd
			,a_status_id
			,a_mac_addr
			,a_case_id
			,a_batch_number
			,a_efiling_status_id
			,a_document_type_cd
			,a_external_efiling_doc_key
			,a_dms_id
			,a_preview_ind
			,a_efiling_viewed_ind
			,a_generated_mode
			,a_efiling_acceptance_ind);
	end;
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
		,a_mac_addr             in varchar2 default null)
	is
	
	begin
		if a_case_hist_id is null or a_case_hist_id < 1 then
			a_case_hist_id := v3owner01.case_history_seq.nextval;
		end if;
		
		insert into v3owner01.case_history(case_hist_id
			,changed_ind
			,short_text
			,long_entry
			,sealed_ind
			,case_hist_entry_id
			,external_source_id
			,case_id
			,external_source_cd
			,display_override_ind
			,parent_entry_id
			,external_dt
			,line_num
			,sequence_num
			,strike_ind
			,error_ind
			,create_dt
			,create_user_id
			,update_dt
			,update_user_id
			,version
			,security_level
			,non_lead_case_id
			,display_case_num
			,mac_addr)
		values(a_case_hist_id
			,a_changed_ind
			,a_short_text
			,a_long_entry
			,a_sealed_ind
			,a_case_hist_entry_id
			,a_external_source_id
			,a_case_id
			,a_external_source_cd
			,a_display_override_ind
			,a_parent_entry_id
			,a_external_dt
			,a_line_num
			,a_sequence_num
			,a_strike_ind
			,a_error_ind
			,a_create_dt
			,a_create_user_id
			,a_update_dt
			,a_update_user_id
			,a_version
			,a_security_level
			,a_non_lead_case_id
			,a_display_case_num
			,a_mac_addr
		);
	end;
	-------------------------------------------------------------------------------------
	procedure p_add_hist_doc_assoc (
		a_case_hist_id    in number
		,a_document_id    in number
		,a_version        in number
		,a_create_dt      in timestamp
		,a_create_user_id in varchar2
		,a_update_dt      in timestamp
		,a_update_user_id in varchar2
		,a_mac_addr       in varchar2)
	is
	
	begin
		insert into v3owner01.case_hist_doc_assoc(
			case_hist_id 
			,document_id
			,version
			,create_dt
			,create_user_id
			,update_dt
			,update_user_id
			,mac_addr)
		values(
			a_case_hist_id
		    ,a_document_id
		    ,a_version
		    ,a_create_dt
		    ,a_create_user_id
		    ,a_update_dt
		    ,a_update_user_id
		    ,a_mac_addr
		);
	end;
	-------------------------------------------------------------------------------------
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
		,a_mac_addr           in varchar2)
	is
		v_note_id	number;
	begin
		if a_note_id is null or a_note_id < 1 then
			a_note_id := v3owner01.note_seq.nextval;
		end if;
		insert into v3owner01.note(
			note_id
			,external_source_id
			,external_source_cd
			,note_type_ind
			,sequence_num
			,text
			,case_year
			,case_id
			,version
			,create_dt
			,create_user_id
			,update_dt
			,update_user_id
			,court_cd
			,mac_addr)
		values(
			a_note_id
			,a_external_source_id
			,a_external_source_cd
			,a_note_type_ind
			,a_sequence_num
			,a_text
			,a_case_year
			,a_case_id
			,a_version
			,a_create_dt
			,a_create_user_id
			,a_update_dt
			,a_update_user_id
			,a_court_cd
			,a_mac_addr);
	end;
	-------------------------------------------------------------------------------------
end;
/