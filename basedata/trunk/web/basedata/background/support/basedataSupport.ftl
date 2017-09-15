<#assign pageTitle = "basedata管理">
<#assign tab1 = ["顶级单位注册","basedata/serial/topUnit.action"]>
<#assign tab2 = ["accountId初始化","basedata/account/accountInit-admin.action"]>
<#assign tab3 = ["EIS参数","system/admin/platformInfoAdmin-systemIniConfig.action?background=1"]>
<#assign tab4 = ["Base参数","basedata/baseInitConfig/baseInitConfig.action"]>
<#assign tab5 = ["号码规则初始化","basedata/coderule/codeRuleSetAdmin.action?initCodeRule=true"]>
<#assign tabList = [tab1,tab2,tab3,tab4,tab5]>

<#include "/common/template/subTabTemplate.ftl"/>

