/*----------------------------sys_model-----------------------------*/
--去掉基础平台模块，将应用平台注册移至系统管理下面
delete from sys_model where id in (3900,3903,3400,3403);
update sys_model set parentid = 3500,orderid = 11,url='system/appregistry/listAppRegistry.action' where id = 3901;
update sys_model set parentid = 3000,orderid = 11,url='system/appregistry/listAppRegistry.action' where id = 3401;

--平台基础信息设置
update sys_model set url = 'system/admin/platformInfoAdmin.action' where id in (3001,3501);

update sys_model set url = 'system/role/roleAdmin.action' where id in (3006,3506);

--应用系统注册
update sys_model set url = 'system/appregistry/appRegistryAdmin.action' where id in (3401,3901);


--模块表：系统管理图片路径
update sys_model set picture = 'system/images/ico_draw.gif' where id= 3000;
update sys_model set picture = 'system/images/icon_sysplat.gif' where id= 3001;
update sys_model set picture = 'system/images/icon_sysgroup.gif' where id= 3003;
update sys_model set picture = 'system/images/icon_sysemployee.gif' where id= 3004;
update sys_model set picture = 'system/images/icon_sysuser.gif' where id= 3005;
update sys_model set picture = 'system/images/icon_sysrole.gif' where id= 3006;
update sys_model set picture = 'system/images/ico_remoteRegister.gif' where id= 3009;
update sys_model set picture = 'system/images/icon_sysserial.gif' where id= 3010;
update sys_model set picture = 'system/images/icon_companysms.gif' where id= 3101;
update sys_model set picture = 'system/images/icon_count.gif' where id= 3102;
update sys_model set picture = 'system/images/icon_finance.gif' where id= 3103;
update sys_model set picture = 'system/images/icon_smsadmin.gif' where id= 3104;
update sys_model set picture = 'system/images/icon_sysregist.gif' where id= 3401;
update sys_model set picture = 'system/images/ico_draw.gif' where id= 3500;
update sys_model set picture = 'system/images/icon_sysplat.gif' where id= 3501;
update sys_model set picture = 'system/images/icon_sysunit.gif' where id= 3502;
update sys_model set picture = 'system/images/icon_sysgroup.gif' where id= 3503;
update sys_model set picture = 'system/images/icon_sysemployee.gif' where id= 3504;
update sys_model set picture = 'system/images/icon_sysuser.gif' where id= 3505;
update sys_model set picture = 'system/images/icon_sysrole.gif' where id= 3506;
update sys_model set picture = 'system/images/ico_remoteRegister.gif' where id= 3509;
update sys_model set picture = 'system/images/icon_sysserial.gif' where id= 3510;
update sys_model set picture = 'system/images/icon_companysms.gif' where id= 3601;
update sys_model set picture = 'system/images/icon_count.gif' where id= 3602;
update sys_model set picture = 'system/images/icon_finance.gif' where id= 3603;
update sys_model set picture = 'system/images/icon_smsadmin.gif' where id= 3604;
update sys_model set picture = 'system/images/icon_sysregist.gif' where id= 3901;

/*----------------------------sys_subsystem-----------------------------*/
--子系统主页地址修改
update sys_subsystem set index_url = '/system/homepage/index.action?appId=' || id where id in (10,11,53,54,55,67,71,72);
update sys_subsystem set index_url = '/system/homepage/smallindex.action?appId=' || id where id in (12,13,66);
update sys_subsystem set index_url = '/system/homepage/systemindex.action' where id = 99;

commit;

/*----------------------------调整表结构-----------------------------*/
drop table SYS_MODULE_LINK;
alter table sys_model add data_subsystems varchar2(20);
update sys_model set data_subsystems = subsystem || ',' where data_subsystems is null;


----学校端系统管理子系统增加“学生家长账号开通”模块
delete sys_model where id = 99001;
insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE,
 UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, 
 ACTIONENABLE, MARK, COMMON, MODEL_ID)
values (99001, '99001', 3000, 7, '学生家长账号开通', 'item', 'system/images/icon_sysgroup.gif', 
'basedata/user/userOpenAdmin.action', null, null, 99, '1,2,3,4,5,6,7,8,    ', 2, 1, '开通学生和家长账号', '', '', '', 
'', '', '', '', '', null, null, 1, '0', '00000000000000000000000000099001');
commit;

--：部门和教职工下面都有维护所有部门/本（下）部门/教职工 的4个节点，因为没有实现相应功能先屏蔽掉
--：部门和教职工下面都有维护所有部门/本（下）部门/教职工 的4个节点，因为没有实现相应功能先屏蔽掉
update sys_model_operation set isactive=0 where operatorname in('维护所有部门信息','维护本（下级）部门信息','维护所有职工信息','维护本（下级）部门职工信息');

update sys_subsystem set curversion = '1.0.0',build = '1103241' where code = 'eis_basedata'; 
commit;

