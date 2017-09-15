
/*==============================================================*/
/* Table: sys_attach_prop                                       */
/*==============================================================*/
create table sys_attach_prop  (
   id                   CHAR(32)                       default sys_guid() not null,
   propvalue            VARCHAR2(255),
   name                 VARCHAR2(255)                   not null,
   constraint PK_SYS_ATTACH_PROP primary key (id, name)
         using index tablespace tbs_eis_sys
)
tablespace tbs_eis_sys
/

/*==============================================================*/
/* Table: sys_attachment                                        */
/*==============================================================*/
create table sys_attachment  (
   id                   CHAR(32)                       default sys_guid() not null,
   objecttype           VARCHAR2(255)                   not null,
   filename             VARCHAR2(255)                   not null,
   filesize             NUMBER(19),
   contenttype          VARCHAR2(255),
   description          VARCHAR2(600),
   creationdate         NUMBER(15),
   modificationdate     NUMBER(15),
   obj_id               CHAR(32),
   unit_id              CHAR(32),
   dir_id               CHAR(32),
   file_path            VARCHAR2(500), 
   creation_time		DATE, 
   modify_time			DATE,
   constraint PK_SYS_ATTACHMENT primary key (id)
         using index tablespace tbs_eis_sys
)
tablespace tbs_eis_sys
/


--20110318
--将系统内置的公共目录的id由'402880932cbb42d8012cbb42d8f50000'改为32个0
update base_storage_dir set id = '00000000000000000000000000000000' where type = 0 and preset = 1;

--旧数据升级
update base_student set dir_id = '00000000000000000000000000000000' where dir_id = '402880932cbb42d8012cbb42d8f50000';
update student_informal set dir_id = '00000000000000000000000000000000' where dir_id = '402880932cbb42d8012cbb42d8f50000';
update base_teacher set dir_id = '00000000000000000000000000000000' where dir_id = '402880932cbb42d8012cbb42d8f50000';
update sys_attachment set dir_id = '00000000000000000000000000000000' where dir_id = '402880932cbb42d8012cbb42d8f50000';
commit;



