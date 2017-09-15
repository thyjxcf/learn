/*==============================================================*/
/* Table: sys_import_template                               	*/
/*==============================================================*/
create table sys_import_template (
   id              		char(32)     	not null,
   unit_id              char(32)     	not null,
   init_file            varchar2(100)  	not null,
   object_name          varchar2(100)    not null,  
   field_name          	varchar2(50)     not null,
   creation_time		DATE 		not null,
   modify_time			DATE 		not null,
   constraint PK_SYS_IMPORT_TEMPLATE primary key (id) using index 
) ;


/*==============================================================*/
/* Index: Index_object_unit                                  			*/
/*==============================================================*/
create index Index_object_unit on sys_import_template (
init_file ASC,
object_name ASC,
unit_id ASC
);


create table sys_scheduler_token(
	id              		char(32)     	not null,
	code					varchar2(30)	not null,--代码：惟一，建议以子系统code打头
	name					varchar2(30)    not null,--名称
	status					number(1)		not null,--状态
	reset_second			number(10)		not null,--重置时间（单位：分钟）	
	modify_time				DATE 			not null,--修改时间
	remark                  varchar2(100)  not null,--说明
	constraint PK_SYS_SCHEDULER_TOKEN primary key (id) using index 	
);

--初始化数据
insert into sys_scheduler_token(id,code,name,status,reset_second,modify_time,remark)
values(sys_guid(),'eis.dataimport','数据导入',0,60,sysdate,'数据导入');


insert into base_mcode_detail(id,mcode_id,this_id,mcode_content,is_using,mcode_type,display_order)
values(sys_guid(),'DM-DRRWZT','4','预导入',1,0,5);


