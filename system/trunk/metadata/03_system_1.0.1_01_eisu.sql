update sys_option set viewable = '0' where iniid = 'SYSTEM.CLASSNAME';
delete from sys_subsystem where id in (53,54,55,67,71,72);
update sys_subsystem set code = 'eis_basedata' where id = 99;
commit;

