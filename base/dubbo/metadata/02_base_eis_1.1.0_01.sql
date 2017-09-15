--产品参数
CREATE TABLE SYS_PRODUCT_PARAM 
   (	ID CHAR(32) default sys_guid() NOT NULL , 
	PARAM_CODE VARCHAR2(50), 
	PARAM_NAME VARCHAR2(255),
	PARAM_VALUE VARCHAR2(255),
  DISPLAY_ORDER NUMBER DEFAULT 0 NOT NULL ,
  DESCRIPTION VARCHAR2(1023)
)

delete from sys_product_param;
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('company.chinese.name','公司中文名称','浙江浙大万朋软件有限公司',1,null);
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('company.english.name','公司英文名称','',2,null);
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('company.copyright','公司版权','Copyright'||'&'||'copy; 1997-currentYear [ZDSoft] All Rights Reserved. 浙大万朋 版权所有',3,null);
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('company.url','公司网址','http://www.zdsoft.net',4,null);
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('commerce.telephone','商务热线','0571-87852778',5,null);
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('support.telephone','技术热线','0571-87852799',6,null);
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('custom.service.telephone','客服热线','0571-87852799',7,null);
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('email','电子邮箱','zdsoft@zdsoft.net',8,null);
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('show.company.logo','是否显示公司logo','1',9,null);
commit;

--升级原有的综合素质
alter table sys_platform_model add (platform INTEGER null);
update sys_platform_model set platform = -2 where subsystem = -2;
update sys_platform_model set platform = -1,subsystem = 67 where id = 2 or parentid = 2;

commit;

--20110622
delete from sys_option where INIID in ('SYSTEM.FAVORITE.SWITCH','SYSTEM.USERNAME.CASE.SENSITIVE.SWITCH');
insert into sys_option (ID, INIID, NAME, DVALUE, DESCRIPTION, NOWVALUE, VIEWABLE, VALIDATEJS, ORDERID, SUBSYSTEMID, COERCIVE)
values ('A6431EF242945511E040007F01007E52', 'SYSTEM.FAVORITE.SWITCH', '是否启用收藏夹功能', '0', '0:不启用，1: 启用', '0', 0, 'check01', 9923, '99', null);
insert into sys_option (ID, INIID, NAME, DVALUE, DESCRIPTION, NOWVALUE, VIEWABLE, VALIDATEJS, ORDERID, SUBSYSTEMID, COERCIVE)
values ('A6431EF242945511E040007F01007E53', 'SYSTEM.USERNAME.CASE.SENSITIVE.SWITCH', '用户名大小写是否敏感', '1', '0:不敏感，1: 敏感', '1', 0, 'check01', 9924, '99', null);

delete from sys_option where iniid in ('SYSTEM.CHECKLICENSE');
insert into sys_option (id, iniid, name, dvalue, description, nowvalue, viewable, validatejs, orderid, subsystemid, coercive)
values ('5E6D9E06402E4B319F87A8663B9EE83A', 'SYSTEM.CHECKLICENSE', '', '1', '0:不启用，1: 启用', '1', 0, 'check01', 9923, '99', null);

commit;


--20110718
delete from sys_product_param where param_code = 'show.company.website.link';
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('show.company.website.link','是否显示公司相关网站链接','1',10,'浙大万朋zdsoft、教育互联edu88、课后网kehou');

commit;


--20110816
alter table base_grade add (grade_code CHAR(2));

//旧数据升级
update base_grade set grade_code = section || (2012 - cast(substr(open_acadyear,1,4) AS integer)) where grade_code is null;
commit;
alter table base_grade modify grade_code CHAR(2) not null;

