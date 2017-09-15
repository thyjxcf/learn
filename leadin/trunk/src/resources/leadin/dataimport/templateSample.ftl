<#-- 标题 -->
<#assign pageTitle = "数据导入模板配置样例">

<#-- 字段依次为：某类导入、说明、xml配置、objectName -->
<#assign tab1 = ["导入1","导入说明1","/multimport/student_import.xml","student_import_sch"]>
<#assign tab2 = ["导入2","导入说明2","/multimport/student_import.xml","student_import_sch"]>
<#assign tabList = [tab1,tab2]>

<#include "/leadin/dataimport/dataImportTemplate.ftl"/>