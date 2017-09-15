--20110525
alter table base_dept add institute_id CHAR(32) DEFAULT '00000000000000000000000000000000' NOT NULL;

--20110601
alter table base_server add context varchar2(32);

--20110616
alter table base_mcode_detail modify THIS_ID VARCHAR2(20) ; 

delete from base_mcode_list WHERE MCODE_ID = 'DM-HMGZSJLX';
insert into base_mcode_list (ID, MCODE_ID, MCODE_NAME, MCODE_LENGTH, MCODE_TYPE, MAINTAIN, MCODE_REMARK, SUBSYSTEM, IS_REPORT, IS_USING)
values ('A5C7668E07A519F1E040007F010061D7', 'DM-HMGZSJLX', '号码规则数据类型', 20, 2, 0, '', 99, 0, 1);

delete from base_mcode_detail WHERE MCODE_ID = 'DM-HMGZSJLX';
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006101', 'DM-HMGZSJLX', 'fixedvalue', '固定代码', 1, 0, 1);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006102', 'DM-HMGZSJLX', 'schregion', '学校所在地行政区', 1, 0, 2);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006103', 'DM-HMGZSJLX', 'schcode', '学校代码', 1, 0, 3);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006104', 'DM-HMGZSJLX', 'runschtype', '学校办别', 1, 0, 4);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006105', 'DM-HMGZSJLX', 'currentacadyear', '当前学年', 1, 0, 5);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006106', 'DM-HMGZSJLX', 'enteracadyear', '入学学年', 1, 0, 6);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006107', 'DM-HMGZSJLX', 'graduateyear', '毕业学年', 1, 0, 7);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006108', 'DM-HMGZSJLX', 'section', '所属学段', 1, 0, 8);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006109', 'DM-HMGZSJLX', 'stusex', '性别', 1, 0, 9);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006110', 'DM-HMGZSJLX', 'identitycard', '身份证', 1, 0, 10);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F0100611', 'DM-HMGZSJLX', 'stusourcetype', '学生来源', 1, 0, 11);
insert into base_mcode_detail (ID, MCODE_ID, THIS_ID, MCODE_CONTENT, IS_USING, MCODE_TYPE, DISPLAY_ORDER)
values ('A5C7668E07A619F1E040007F01006112', 'DM-HMGZSJLX', 'stuislocalsource', '是否本地生源', 1, 0, 12);
commit;

--20110617
alter table sym_user add dept_id char(32);

create or replace trigger SYM_I_BS_USER after insert on BASE_USER                                
for each row
begin
  if (:new.EVENT_SOURCE=0) or (:new.EVENT_SOURCE is null)  then
    insert into sym_USER (event_type,sym_id,id,unit_id,sequence,account_id,owner_id,owner_type,username,password,real_name,user_state,user_type,email,region_code,display_order,sex,charge_number,charge_number_type,order_status,nick_name,creation_time,modify_time,is_deleted,event_source,dept_id)
    values( 'I',sys_guid(),:new.id,:new.unit_id,:new.sequence,:new.account_id,:new.owner_id,:new.owner_type,:new.username,:new.password,:new.real_name,:new.user_state,:new.user_type,:new.email,:new.region_code,:new.display_order,:new.sex,:new.charge_number,:new.charge_number_type,:new.order_status,:new.nick_name,:new.creation_time,:new.modify_time,:new.is_deleted,:new.event_source,:new.dept_id);
   end if;
 end;
/


create or replace trigger SYM_U_BS_USER after update on BASE_USER
for each row
begin
  if (:new.EVENT_SOURCE=0) or (:new.EVENT_SOURCE is null)  then
    insert into sym_USER (event_type,sym_id, id,unit_id,sequence,account_id,owner_id,owner_type,username,password,real_name,user_state,user_type,email,region_code,display_order,sex,charge_number,charge_number_type,order_status,nick_name,creation_time,modify_time,is_deleted,event_source,dept_id)
    values(decode(:new.is_deleted,1,'D',0,'U'),sys_guid(),:new.id,:new.unit_id,:new.sequence,:new.account_id,:new.owner_id,:new.owner_type,:new.username,:new.password,:new.real_name,:new.user_state,:new.user_type,:new.email,:new.region_code,:new.display_order,:new.sex,:new.charge_number,:new.charge_number_type,:new.order_status,:new.nick_name,:new.creation_time,:new.modify_time,:new.is_deleted,:new.event_source,:new.dept_id);
  end if;
end;
/






