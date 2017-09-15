--将数据库中的籍贯、来源地区和出生地进行升级(2位或4位升6位)，
--这是由于原来的导入取值有误引起的，现在已经修改
--正式表
update base_student set source_place = concat(trim(source_place) , '00')
where length(trim(source_place)) = 4;

update base_student set source_place = concat(trim(source_place) , '0000')
where length(trim(source_place)) = 2;

update base_student set native_place = concat(trim(native_place) , '00')
where length(trim(native_place)) = 4;

update base_student set native_place = concat(trim(native_place) , '0000')
where length(trim(native_place)) = 2;

update base_student_ex set homeplace = concat(trim(homeplace) , '00')
where length(trim(homeplace)) = 4;

update base_student_ex set homeplace = concat(trim(homeplace) , '0000')
where length(trim(homeplace)) = 2;
commit;

alter table base_import_job modify server_type_id NUMBER(10) default 1 not null ;
update base_import_job set server_type_id = 1 where server_type_id is null;
commit;

--教育局学年学期base_semester增加课长和注册日期两个字段，这样学校端的学年学期直接从教育局端读取
alter table base_semester  add  CLASS_HOUR NUMBER  default 45;
alter table base_semester  add  REGISTER_DATE DATE ;

--教育局基础信息中IS_NATION_POVERTY是微代码，所以改为非boolean的名称
ALTER table base_eduinfo rename column IS_NATION_POVERTY to NATION_POVERTY ;

--隐藏教师信息的点到卡号，后面定下来基础库后，再看怎么处理
update stusys_cols_display set cols_use=0 where cols_type='teacher' and cols_name='点到卡号';
commit;
--隐藏教师信息的排序号
update stusys_cols_display set cols_use=0 where cols_type='teacher' and cols_name='排序号';
commit;
