<#include "/leadin/dataimport/dataimport.ftl"/> 

<#-- 参数信息 -->
<#macro displayInfoMacro>
<div class="query-part">
<input type="hidden" name="activityId" value="${activityId!}"/>
<input type="hidden" name="selectYear" value="${selectYear!}"/>
</div>

<script>
function validateTemplate(){	
	return true;	
}

function validateImport(){	
	return true;	
}

</script> 
</#macro>

<#-- 说明信息 -->
<#macro importDescriptionMacro>
1、导入文件只支持Excel文件(*.xls或者*.xlsx)。<br />
</#macro>

<#-- 按钮栏 -->
<#macro buttonBar>
<a href="javascript:void(0);" class="abtn-blue-big" onclick="myback();">返回</a>
<script>
function myback(){
	load("#container","${request.contextPath}/office/salarymanage/salarymanage.action");
}
</script>
</#macro>

<#macro remark>
<tr><td height="10"></td></tr>	
 		<td>1、所有信息均应认真核对，确保数据真实、准确、完整。<br>
 		<font color='red'>2、注意：本导入依照工资项次名来确定是否覆盖，下载完模板请修改excel名(如：2016年1月第一次发放工资，年份必须４位，月份至少1位，否则视为导入无效；项次名最大长度２０汉字；下载模板的第一行需要自己维护对应的列名；注：项次名最大长度为20个汉字)。</font><br>
	</td>	 					
</#macro>