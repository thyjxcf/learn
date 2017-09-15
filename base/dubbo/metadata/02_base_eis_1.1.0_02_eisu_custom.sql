delete from sys_product_param;
insert into sys_product_param(param_code,param_name,param_value,display_order,description)
values('show.company.logo','是否显示公司logo','0',9,null);
commit;

update sys_version set name = '中职数字校园综合应用平台' where productid = 'EISU-S';
commit;


delete from sys_option where iniid in ('SYSTEM.TREE.SHOW.INSTITUTE.SWITCH');
insert into sys_option (id, iniid, name, dvalue, 
  description, nowvalue, viewable, validatejs, orderid, subsystemid, coercive)
values ('70A79973E2C447EFAB869EDC5326A5B3', 'SYSTEM.TREE.SHOW.INSTITUTE.SWITCH', '是否显示院系', '1',
  '0:不显示，1: 显示', '1', 2, 'check01', 9923, '99', null);

delete from sys_option where iniid in ('SYSTEM.TREE.SHOW.SPECIALTY.POINT.SWITCH');
insert into sys_option (id, iniid, name, dvalue, 
  description, nowvalue, viewable, validatejs, orderid, subsystemid, coercive)
values ('AA45863395784BC28615E4D874462749', 'SYSTEM.TREE.SHOW.SPECIALTY.POINT.SWITCH', '是否显示专业方向', '1',
  '0:不显示，1: 显示', '1', 2, 'check01', 9924, '99', null);

delete from sys_option where iniid in ('SYSTEM.TREE.SHOW.GRADE.SWITCH');
insert into sys_option (id, iniid, name, dvalue, 
  description, nowvalue, viewable, validatejs, orderid, subsystemid, coercive)
values ('04AE54943A8C4E0ABDE183686DC7B4D9', 'SYSTEM.TREE.SHOW.GRADE.SWITCH', '是否显示年级', '1',
  '0:不显示，1: 显示', '1', 2, 'check01', 9925, '99', null);

delete sys_model where id = 99016;
insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, 
  URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE,
  UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, 
  MARK, COMMON, MODEL_ID)
values (99016, '99016', 3000, 8, '公共树参数设置', 'item', 'system/images/icon_sysgroup.gif', 
  'system/admin/treeParamAdmin.action', null, null, 99, '1,2,3,4,5,6,7,8,    ', 
  2, 1, '公共树参数设置', '', '', '', '', '', '', '', '', null, null, 
  1, '0', 'A8C2F6D53F0345C3A14413EB099278E2');
commit;

--单位信息选项初始化
delete from sys_systemini_unit where iniid in ('SYSTEM.TREE.SHOW.INSTITUTE.SWITCH');
insert into sys_systemini_unit (unitid, iniid, name, dvalue, 
  description, nowvalue, viewable, validatejs, orderid  )
select id , 'SYSTEM.TREE.SHOW.INSTITUTE.SWITCH', '是否显示院系', '1',
  '0:不显示，1: 显示', '1', 2, 'check01', 9924  from base_unit;

delete from sys_systemini_unit where iniid in ('SYSTEM.TREE.SHOW.SPECIALTY.POINT.SWITCH');
insert into sys_systemini_unit (unitid, iniid, name, dvalue, 
  description, nowvalue, viewable, validatejs, orderid  )
select id , 'SYSTEM.TREE.SHOW.SPECIALTY.POINT.SWITCH', '是否显示专业方向', '1',
  '0:不显示，1: 显示', '1', 2, 'check01', 9925  from base_unit;

delete from sys_systemini_unit where iniid in ('SYSTEM.TREE.SHOW.GRADE.SWITCH');
insert into sys_systemini_unit (unitid, iniid, name, dvalue, 
  description, nowvalue, viewable, validatejs, orderid )
select id, 'SYSTEM.TREE.SHOW.GRADE.SWITCH', '是否显示年级', '1',
  '0:不显示，1: 显示', '1', 2, 'check01', 9926  from base_unit;




