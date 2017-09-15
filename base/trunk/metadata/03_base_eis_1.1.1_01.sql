  alter table sys_attachment add ( 
      ext_name      varchar2(20)  null,        --文件扩展名 
      status        number(1)    default 9 null,    --转换状态（0等待转换 1正在转换 2转换结束 3转换出错 4 预转换 9不需要转换）
      job_run_time    date            null,    --转换开始时间
      job_end_time    date            null,    --转换结束时间
      result_msg        	varchar2(1000)  null		--转换结果信息
  )

--对旧数据进行升级
update sys_attachment set status = 9,ext_name = substr(filename,instr(filename,'.',-1,1) +1) where ext_name is null;

commit;

--sys_scheduler_token
delete from sys_scheduler_token where code in('eis.resource.file');
insert into sys_scheduler_token(id,code,name,status,reset_second,modify_time,remark)
values(sys_guid(),'eis.resource.file','资源文件转换',0,60,sysdate,'资源文件转换');
commit;

--资源转换参数
delete from sys_option where INIID in ('SYSTEM.CONVERTOR.PDF.2.TEMP','SYSTEM.CONVERTOR.RESOURCE.MARK.IMG.PATH',
       'SYSTEM.CONVERTOR.RESOURCE.MARK.IMG.PATH.END','SYSTEM.CONVERTOR.RESOURCE.PDF.READONLY',
       'SYSTEM.CONVERTOR.TYPE.DOCUMENT','SYSTEM.CONVERTOR.TYPE.VIDEO',
       'SYSTEM.CONVERTOR.ADD.MARK.RESOURCE.TYPE');
       
insert into sys_option (ID, INIID, NAME, DVALUE, DESCRIPTION, NOWVALUE, VIEWABLE, VALIDATEJS, ORDERID, SUBSYSTEMID, COERCIVE)
values ('F3519CF527185285E040007F01005CF1', 'SYSTEM.CONVERTOR.PDF.2.TEMP', '文档转换成pdf时是否启用临时目录', '0', '0:不启用，1: 启用', '0', 1, 'check01', 9941, '99', null);

insert into sys_option (ID, INIID, NAME, DVALUE, DESCRIPTION, NOWVALUE, VIEWABLE, VALIDATEJS, ORDERID, SUBSYSTEMID, COERCIVE)
values ('F3519CF527185285E040007F01005CF2', 'SYSTEM.CONVERTOR.RESOURCE.MARK.IMG.PATH', '修改资源转换中的水印图片页头（建议大小=100*100）', '', '', '', 1, '', 9942, '99', null);

insert into sys_option (ID, INIID, NAME, DVALUE, DESCRIPTION, NOWVALUE, VIEWABLE, VALIDATEJS, ORDERID, SUBSYSTEMID, COERCIVE)
values ('F3519CF527185285E040007F01005CF3', 'SYSTEM.CONVERTOR.RESOURCE.MARK.IMG.PATH.END', '修改资源转换中的水印图片页尾（建议大小=100*100）', '', '', '', 1, '', 9943, '99', null);

insert into sys_option (ID, INIID, NAME, DVALUE, DESCRIPTION, NOWVALUE, VIEWABLE, VALIDATEJS, ORDERID, SUBSYSTEMID, COERCIVE)
values ('F3519CF527185285E040007F01005CF4', 'SYSTEM.CONVERTOR.RESOURCE.PDF.READONLY', '添加水印后是否需要加密pdf文档', '0', '0:不加密，1: 加密', '0', 1, 'check01', 9944, '99', null);

insert into sys_option (ID, INIID, NAME, DVALUE, DESCRIPTION, NOWVALUE, VIEWABLE, VALIDATEJS, ORDERID, SUBSYSTEMID, COERCIVE)
values ('F3519CF527185285E040007F01005CF5', 'SYSTEM.CONVERTOR.TYPE.DOCUMENT', '文档转换文件格式', 'rtf,doc,docx,xls,xlsx,csv,ppt,pptx,pdf', '', 'rtf,doc,docx,xls,xlsx,csv,ppt,pptx,pdf', 1, '', 9945, '99', null);

insert into sys_option (ID, INIID, NAME, DVALUE, DESCRIPTION, NOWVALUE, VIEWABLE, VALIDATEJS, ORDERID, SUBSYSTEMID, COERCIVE)
values ('F3519CF527185285E040007F01005CF6', 'SYSTEM.CONVERTOR.TYPE.VIDEO', '视频转换文件格式', 'avi,mpg,wmv,3gp,mov,mp4,asf,asx,flv,wmv9,rm,rmvb', '', 'avi,mpg,wmv,3gp,mov,mp4,asf,asx,flv,wmv9,rm,rmvb', 1, '', 9946, '99', null);

insert into sys_option (ID, INIID, NAME, DVALUE, DESCRIPTION, NOWVALUE, VIEWABLE, VALIDATEJS, ORDERID, SUBSYSTEMID, COERCIVE)
values ('F3519CF527185285E040007F01005CF7', 'SYSTEM.CONVERTOR.ADD.MARK.RESOURCE.TYPE', '需要添加水印的课程资源类型（格式为如：,1,）', '', '', '', 1, '', 9947, '99', null);


commit;

