
delete from sys_model where subsystem = 99 and unitclass = 1;

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (99501, 'SYS299', -1, 0, '基础数据', 'dir', '', '', null, null, 99, '1,2,                ', 1, 1, '基础数据', '', '', '', '', '', '', '', '', null, null, 1, '0', '00000000000000000000000000099501', '99,');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (99502, 'SYS002', 99501, 1, '单位管理', 'item', 'system/images/icon_sysunit.gif', 'basedata/unit/unitAdmin.action', null, null, 99, '1,2,                ', 1, 1, '单位管理', '', '', '', '', '', '', '', '', null, null, 1, '0', '00000000000000000000000000099502', '99,');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (99503, 'SYS003', 99501, 2, '部门管理', 'item', 'system/images/icon_sysgroup.gif', 'basedata/unit/deptAdmin.action', null, null, 99, '1,2,                ', 1, 1, '管理各个组内的成员信息', '', '', '', '', '', '', '', '', null, null, 1, '0', '00000000000000000000000000099503', '99,');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (99504, 'EDU001', 99501, 3, '教育局基本信息', 'item', 'basedata/images/icon_edu.gif', 'basedata/edu/eduinfo.action', null, null, 99, '1,2,                ', 1, 1, '教育局基本信息设置', '', '', '', '', '', '', '', '', null, null, 1, '0', '00000000000000000000000000099504', '');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (99505, 'EDU003', 99501, 4, '全局学年学期设置', 'item', 'basedata/images/icon_semester.gif', 'basedata/edu/semesterAdmin.action', null, null, 99, '1,2,                ', 1, 1, '教育局端全局学年学期设置', '', '', '', '', '', '', '', '', null, null, 1, '0', '00000000000000000000000000099505', '');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (99506, 'SYS214', 99501, 5, '节假日设置', 'item', 'basedata/images/icon_holiday.gif', 'basedata/holiday/baseDateAdmin.action', null, null, 99, '1,2,                ', 1, 1, '节假日设置', '', '', '', '', '', '', '', '', null, null, 1, '0', '00000000000000000000000000099506', '99,');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (3500, 'SYS000', -1, 1, '系统管理', 'dir', 'system/images/ico_draw.gif', '', null, null, 99, '1,2,3,4,5,6,7,8,    ', 1, 1, '系统管理', '', '', '', '', '', '', '', '', null, null, 1, '0', '20BC8039DA3C4D4AA72BE80C50E4FB4F', '99,');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (3501, 'SYS001', 3500, 1, '平台基础信息设置', 'item', 'system/images/icon_sysplat.gif', 'system/admin/platformInfoAdmin.action', null, null, 99, '1,2,3,4,5,6,7,8,    ', 1, 1, '平台的基本信息设置', '', '', '', '', '', '', '', '', null, null, 1, '0', '0D8440DB5FE04A8A88CBB9C12881D67E', '99,');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (3504, 'SYS004', 3500, 4, '教职工管理', 'item', 'system/images/icon_sysemployee.gif', 'basedata/teacher/teacherAdmin.action', null, null, 99, '1,2,3,4,5,6,7,8,    ', 1, 1, '职工管理', '', '', '', '', '', '', '', '', null, null, 1, '0', '14E07C17121D40CCBCAEF94EADDF2B14', '99,');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (3505, 'SYS005', 3500, 5, '用户管理', 'item', 'system/images/icon_sysuser.gif', 'basedata/user/userAdmin.action', null, null, 99, '1,2,3,4,5,6,7,8,    ', 1, 1, '用户管理', '', '', '', '', '', '', '', '', null, null, 1, '0', '5140344ADAD045499AB291FBBF01FB34', '99,');

insert into sys_model (ID, MID, PARENTID, ORDERID, NAME, TYPE, PICTURE, URL, WIDTH, HEIGHT, SUBSYSTEM, USERTYPE, UNITCLASS, ISASSIGNED, DESCRIPTION, WIN, PBCOMMON, LIMIT, VERSION, FILELIST, RELDIR, MAINFILE, PARM, USELEVEL, ACTIONENABLE, MARK, COMMON, MODEL_ID, DATA_SUBSYSTEMS)
values (3506, 'SYS006', 3500, 6, '角色管理', 'item', 'system/images/icon_sysrole.gif', 'system/role/roleAdmin.action', null, null, 99, '1,2,3,4,5,6,7,8,    ', 1, 1, '角色管理', '', '', '', '', '', '', '', '', null, null, 1, '0', '8C286615DC13495393D94E9F66AD6B16', '99,');

commit;


