alter table stu_platform_model rename to sys_platform_model;

alter table sys_platform_model add data_subsystems varchar2(20);
update sys_platform_model set data_subsystems = subsystem || ',' where data_subsystems is null;

create table base_storage_dir (
       id char(32) not null primary key,
       type smallint not null,
       active smallint not null,
       preset smallint not null,
       dir varchar2(100)
);

--系統內置公用目录
insert into base_storage_dir(id,type,active,preset,dir)
values('402880932cbb42d8012cbb42d8f50000',0,1,1,'store');


--旧数据升级需要依靠程序来升级
alter table sys_attachment add (dir_id char(32) , file_path varchar2(500), creation_time date, modify_time date);

commit;

