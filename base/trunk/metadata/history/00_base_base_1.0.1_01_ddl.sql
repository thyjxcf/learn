--20110126
alter table base_teacher add (dir_id CHAR(32) null,file_path VARCHAR2(500) null);

--20110314
alter table LOG_MODULE_USE add (server_type_id NUMBER(10) null,subsystem_id NUMBER null);
alter table LOG_MODULE_USE rename column  ACCCOUNT_ID to ACCOUNT_ID;

--20110402
alter table sym_user add (password varchar2(64) null);
create or replace trigger SYM_I_BS_USER after insert on BASE_USER
                                for each row
begin
                                  if (:new."EVENT_SOURCE"=0) or (:new."EVENT_SOURCE" is null)  then
                                    insert into sym_USER (event_type,sym_id,
                                     id,unit_id,sequence,account_id,owner_id,owner_type,username,real_name,user_state,user_type,email,region_code,display_order,sex,charge_number,charge_number_type,order_status,nick_name,creation_time,modify_time,is_deleted,event_source,password
                                                                           )
                                    values(
                                      'I',sys_guid(),
  :new.id,:new.unit_id,:new.sequence,:new.account_id,:new.owner_id,:new.owner_type,:new.username,:new.real_name,:new.user_state,:new.user_type,:new.email,:new.region_code,:new.display_order,:new.sex,:new.charge_number,:new.charge_number_type,:new.order_status,:new.nick_name,:new.creation_time,:new.modify_time,:new.is_deleted,:new.event_source,:new.password
                                      );
                                  end if;
                                end;
/
ALTER TRIGGER SYM_I_BS_USER ENABLE;

create or replace trigger SYM_U_BS_USER after update on BASE_USER
                                for each row
begin
                                  if (:new."EVENT_SOURCE"=0) or (:new."EVENT_SOURCE" is null)  then
                                    insert into sym_USER (event_type,sym_id,
                             id,unit_id,sequence,account_id,owner_id,owner_type,username,real_name,user_state,user_type,email,region_code,display_order,sex,charge_number,charge_number_type,order_status,nick_name,creation_time,modify_time,is_deleted,event_source,password
                                    )
                                    values(
                                     decode(:new.is_deleted,1,'D',0,'U'),sys_guid(),
      :new.id,:new.unit_id,:new.sequence,:new.account_id,:new.owner_id,:new.owner_type,:new.username,:new.real_name,:new.user_state,:new.user_type,:new.email,:new.region_code,:new.display_order,:new.sex,:new.charge_number,:new.charge_number_type,:new.order_status,:new.nick_name,:new.creation_time,:new.modify_time,:new.is_deleted,:new.event_source,:new.password
                                    );
                                  end if;
                                end;
/
ALTER TRIGGER SYM_U_BS_USER ENABLE;

--20110413 base_sys_option base_ware 的id改为大写
update base_sys_option set id = upper(id);
update base_ware set id = upper(id);
commit;


