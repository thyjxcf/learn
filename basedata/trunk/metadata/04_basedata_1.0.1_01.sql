/*----------------------------sys_model-----------------------------*/
delete sys_model where id in ( 99012,99013 );
insert into SYS_MODEL (ID,MID,PARENTID,ORDERID,NAME,TYPE,PICTURE,URL,WIDTH,HEIGHT,SUBSYSTEM,USERTYPE,UNITCLASS,ISASSIGNED,DESCRIPTION,WIN,PBCOMMON,LIMIT,VERSION,FILELIST,RELDIR,MAINFILE,PARM,USELEVEL,ACTIONENABLE,MARK,COMMON,MODEL_ID) 
values (99012,'SYS212',3000,212,'订购管理','item','basedata/images/icon_sysorder.gif','basedata/order/serverAuthorize.action',null,null,99,'1,2,3,4,5,6,7,8,',2,1,'订购管理',null,null,null,null,null,null,null,null,null,null,1,'0','00000000000000000000000000099012');

insert into SYS_MODEL (ID,MID,PARENTID,ORDERID,NAME,TYPE,PICTURE,URL,WIDTH,HEIGHT,SUBSYSTEM,USERTYPE,UNITCLASS,ISASSIGNED,DESCRIPTION,WIN,PBCOMMON,LIMIT,VERSION,FILELIST,RELDIR,MAINFILE,PARM,USELEVEL,ACTIONENABLE,MARK,COMMON,MODEL_ID) 
values (99013,'SYS212',3500,213,'订购管理','item','basedata/images/icon_sysorder.gif','basedata/order/serverAuthorize.action',null,null,99,'1,2,3,4,5,6,7,8,',1,1,'订购管理',null,null,null,null,null,null,null,null,null,null,1,'0','00000000000000000000000000099013');

commit;

