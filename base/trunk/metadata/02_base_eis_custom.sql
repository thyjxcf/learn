--定制
--20110622 广州教育城：用户名大小写不敏感
update sys_option set DVALUE = '0',NOWVALUE = '0' WHERE INIID = 'SYSTEM.USERNAME.CASE.SENSITIVE.SWITCH';

commit;




