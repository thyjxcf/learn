/*----------------------------sys_model-----------------------------*/
update sys_model set url = 'basedata/sch/basicClassAdmin.action' where id =509;
update sys_model set url = 'basedata/sch/schoolinfo.action' where id =506;
update sys_model set url = 'basedata/sch/basicSemesterAdmin.action' where id =507;

update sys_model set url = 'basedata/unit/unitAdmin.action' where id =3502;
update sys_model set url = 'basedata/unit/deptAdmin.action' where id =3503;
update sys_model set url = 'basedata/unit/remoteRegister.action' where id =3509;

update sys_model set url = 'basedata/unit/deptAdmin.action' where id =3003;
update sys_model set url = 'basedata/unit/remoteRegister.action' where id =3009;

update sys_model set url = 'basedata/edu/eduinfo.action' where id =7;
update sys_model set url = 'basedata/edu/stuAuditConfigAdmin.action' where id =137;
update sys_model set url = 'basedata/edu/schDistrict.action' where id =28;
update sys_model set url = 'basedata/edu/semesterAdmin.action' where id =9;

update sys_model set url = 'basedata/teacher/teacherAdmin.action' where id =3004;
update sys_model set url = 'basedata/teacher/teacherAdmin.action' where id =3504;

update sys_model set url = 'basedata/user/userAdmin.action' where id =3005;
update sys_model set url = 'basedata/user/userAdmin.action' where id =3505;

update sys_model set url = 'basedata/serial/serialRegister.action' where id in (3010,3510);


--单位远程注册
update sys_model set url = 'basedata/unit/unitAdmin-remoteRegister.action' where id in (3009,3509);
--序列号注册
update sys_model set url = 'basedata/serial/serialAdmin-register.action' where id in (3010,3510);

--号码规则设置
update sys_model set url = 'basedata/coderule/codeRuleSetAdmin.action' where id in(508,10);

--学校端和教育局端增加“学生家庭信息维护”模块，将原来学生信息维护里的家庭信息维护移到这里（为了适应新的权限校验）
delete sys_model where id = 10005;
Insert into sys_model (id,mid,parentid,orderid,name,type,picture,url,width,height,subsystem,usertype,unitclass,
isassigned,description,win,pbcommon,limit,version,filelist,reldir,mainfile,parm,uselevel,actionenable,mark,common,model_id) 
values (10005,'10005',502,3,'学生家庭信息维护','jsp','images/SCH010.gif','basedata/stu/familyAdmin.action',
null,null,10,'1,3,4,5,6,7,8,      ',2,null,'学生家庭信息维护',null,null,'1',null,null,null,null,null,null,null,1,'0','00000000000000000000000000010005');

delete sys_model where id = 10006;
Insert into sys_model (id,mid,parentid,orderid,name,type,picture,url,width,height,subsystem,usertype,unitclass,
isassigned,description,win,pbcommon,limit,version,filelist,reldir,mainfile,parm,uselevel,actionenable,mark,common,model_id) 
values (10006,'10006',6,2,'学生家庭信息维护','jsp','images/EDU030.gif','basedata/stu/familyAdmin.action',
null,null,10,'1,2,                ',1,null,'学生家庭信息维护',null,null,'',null,null,null,null,null,null,null,1,'0','00000000000000000000000000010006');

/*----------------------------调整表结构-----------------------------*/
--日期表
alter table stusys_date_info drop column date_int_id;
alter table stusys_date_info rename to sys_date_info;

drop trigger TR_STUDYD_DATE_INFO_I;

--单位内惟一的号码表：如学号
alter table stusys_stucode_rule rename to sys_unit_code_rule;
alter table stusys_stucode_rule_list rename to sys_unit_code_rule_detail;

--全局惟一的号码表：如学籍号、会考证号等
alter table stusys_unitivecode_rule rename to sys_code_rule;

--单位参数表
insert into sys_systemini_unit select * from stusys_systemini_unit;

drop table stusys_systemini_unit;

alter table sys_unit_code_rule modify code_type char(2);
update sys_unit_code_rule set code_type = '21' where trim(code_type) = '1';
update sys_unit_code_rule set code_type = '22' where trim(code_type) = '2';

update sys_code_rule set type = 11 where type = 0;
update sys_code_rule set type = 12 where type = 1;

commit;

--学校端“学年学期设置”名称改为“日期维护”。
update sys_model set name='日期维护' where name='学年学期设置' and unitclass=2;
commit;

update sys_model set url = 'basedata/query/schoolGeneralInfoQuery.action' where id = 16;
commit;

