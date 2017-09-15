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
</script> 
</#macro>

<#-- 说明信息 -->
<#macro importDescriptionMacro>
1、导入文件只支持Excel文件(*.xls，*.xlsx)<br />
2、导入文件内容全部为实际显示内容，如性别为：男或者女<br />
3、日期类型支持三种格式(年月日、年-月-日、年/月/日)，其中【年月日】这种格式必须满足8位数，即月份和日期不足两位的，前面要补0，如20060102。<br />
<font color='red'><Strong>注意：</strong></font><font color='blue'>日期数据时，如果不是采用【年月日】这种格式时，需要把Excel的单元格格式设置为常规或者文本，否则导入时，格式验证无法通过！</font><br>                          
</#macro>

<#-- 按钮栏 -->
<#macro buttonBar>
    <a class="abtn-blue-big" onclick="goback();">返回</a>
	<script>
	function goback(){
		load("#container","${request.contextPath}/basedata/teacher/teacherAdmin.action");
	}
	</script>
</#macro>

<#macro remark>
<#assign EDU=stack.findValue('@net.zdsoft.eis.system.constant.SystemConstant@TEACHER_IMPORT_EDU')>
  <table>
  	<tr><td><font color='blue'>1、不选择“覆盖原有数据”时，只增加系统里没有的教职工信息；系统中已有的教职工信息，内容不变；<br>
		 2、选择“覆盖原有数据”时，增加系统里没有的教职工信息，同时更新系统里已有的教职工信息<br>
		 <#if objectName == EDU>
		 	&nbsp;&nbsp;（只更新导入文件中有的列内容【所属单位、职工姓名、用户名和职工编号除外】，导入文件中没有的列，则保持系统中的内容不变）</font><br>
		 	<font color='red'>3、注意：选择“覆盖原有数据”时，更新教职工信息的依据是所属单位、姓名和编号</font><br>
		 <#else>
		 	&nbsp;&nbsp;（只更新导入文件中有的列内容【姓名和编号除外】，导入文件中没有的列，则保持系统中的内容不变）</font><br>
		 	<font color='red'>3、注意：选择“覆盖原有数据”时，更新教职工信息的依据是姓名和编号</font><br>
		 </#if>
     </td></tr>
  </table>
</#macro>

