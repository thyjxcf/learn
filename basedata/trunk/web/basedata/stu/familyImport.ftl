<#include "/leadin/dataimport/dataimport.ftl"/> 
<#-- 参数信息 -->
<#macro displayInfoMacro>
<script>
function validateTemplate(){
	return true;	
}

function validateImport(){	
	return true;	
}
<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
function blackFamList(){
	load("#famDiv","${request.contextPath}/stusys/sch/student/studentadmin-familyAdmin-list.action?studentId=${studentId!}");
}
</#if>
</script> 
<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
<input type="hidden" id="studentId" name="studentId" value="${studentId!}"/>
</#if>
</#macro>

<#-- 说明信息 -->
<#macro importDescriptionMacro>
1、导入文件只支持Excel文件(*.xls或者*.xlsx)<br />
2、导入文件内容全部为实际显示内容，如性别为：男或者女<br />
3、日期类型支持三种格式(年月日、年-月-日、年/月/日)，其中【年月日】这种格式必须满足8位数，即月份和日期不足两位的，前面要补0，如20060102。<br />
<font color='red'><Strong>注意：</strong>日期数据时，如果不是采用【年月日】这种格式时，需要把Excel的单元格格式设置为常规或者文本，否则导入时，格式验证无法通过！</font><br>                          
</#macro>

<#-- 按钮栏 -->
<#macro buttonBar>
<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
<a href="javascript:void(0);" class="abtn-blue-big" onclick="blackFamList();">返回</a>
</#if>
</#macro>

<#macro remark>
  	  <tr><td height="10"></td></tr>
   	  <td>1、不选择“覆盖原有数据”时，只增加系统里没有的家庭成员信息；系统中已有的家庭成员信息，内容不变；<br>
			 2、选择“覆盖原有数据”时，增加系统里没有的家庭成员信息，同时更新系统里已有的家庭成员信息<br>
			 &nbsp;&nbsp;（只更新导入文件中有的列内容【学生学号、学生姓名、家长姓名和关系除外】，导入文件中没有的列，则保持系统中的内容不变） </br>
	     	<font color='red'>注意：选择“覆盖原有数据”时，更新学生家庭成员信息的依据是学生学号、学生姓名、家长姓名和关系</font></br>
	     </td>
</#macro>
